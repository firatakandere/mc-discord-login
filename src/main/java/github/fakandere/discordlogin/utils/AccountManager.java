package github.fakandere.discordlogin.utils;

import com.google.inject.Inject;
import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.exceptions.RegisterUserException;
import github.fakandere.discordlogin.repositories.user.IUserRepository;
import github.fakandere.discordlogin.DiscordLoginPlugin;
import github.fakandere.discordlogin.exceptions.UnregisterUserException;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class AccountManager implements IAccountManager {

    @Inject
    public AccountManager(
            DiscordLoginPlugin plugin,
            IMessages messages,
            IConfig config,
            IUserRepository userRepository
    ) {
        this.plugin = plugin;
        this.messages = messages;
        this.config = config;
        this.userRepository = userRepository;

        discordRegisterCodes = new HashMap<>();
        loggedInUsers = new DualHashBidiMap<>(); // playerUUID, discordUserID
        awaitingLogins = new HashMap<>(); // discordUserID, (discordMessageID, playerUUID)
    }

    private final Map<String, UUID> discordRegisterCodes;
    private final Map<String, Pair<String, UUID>> awaitingLogins;
    private final BidiMap<UUID, String> loggedInUsers;

    private final DiscordLoginPlugin plugin;
    private final IMessages messages;
    private final IConfig config;
    private final IUserRepository userRepository;

    // @todo any better way?
    public void onEnable() {
        refreshBotStatus();
    }

    public void initializePlayer(final Player p) {
        /* this can be cancelled when user logins/registers or leaves
         * but this keeps the code simple for now
         */
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            if (!isLoggedIn(p)) {
                p.kickPlayer(messages.getServerTimeoutFailure());
            }
        }, 20L * config.getServerTimeoutInSeconds());
        if (userRepository.isRegistered(p.getUniqueId())) {
            try {
                handleLogin(p);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "failed", e);
                // @todo handle this
            }
        } else {
            this.handleRegister(p);
        }
    }

    public void registerPlayer(final String registrationCode, final String discordUserID) throws RegisterUserException, NotFoundException {
        final UUID playerID = discordRegisterCodes.getOrDefault(registrationCode, null);

        if (playerID == null) {
            throw new NotFoundException();
        }

        userRepository.registerUser(playerID, discordUserID);

        // Log user in.
        finalizeLogin(discordUserID, playerID);

        // Clear
        discordRegisterCodes.remove(registrationCode);
    }

    public void unregisterPlayer(final String discordUserID) throws NotFoundException, UnregisterUserException {
        loggedInUsers.removeValue(discordUserID); // make sure user is not in loggedInUsers anymore
        userRepository.unregisterUser(discordUserID);
    }

    public void handleUserReaction(final String discordUserID, final String discordMessageID, final String emote) {
        if (emote.equals(config.getDiscordButtonsReject())) {
            awaitingLogins.remove(discordUserID);
        }
        else if (emote.equals(config.getDiscordButtonsAccept()) && awaitingLogins.containsKey(discordUserID)) {
            final Pair<String, UUID> pair = awaitingLogins.get(discordUserID);
            if (pair.getLeft().equals(discordMessageID)) {
                this.finalizeLogin(discordUserID, pair.getRight());
            }
        }
    }

    public boolean isLoggedIn(final Player p) {
        return loggedInUsers.containsKey(p.getUniqueId());
    }

    public Map<UUID, String> getLoggedInUsers() {
        return loggedInUsers;
    }

    public void handlePlayerQuit(final Player p) {
        final String discordID = loggedInUsers.get(p.getUniqueId());
        loggedInUsers.remove(p.getUniqueId());
        awaitingLogins.remove(discordID);
        discordRegisterCodes.remove(discordID);
        refreshBotStatus();
    }

    private void finalizeLogin(final String discordUserID, final UUID playerID) {
        loggedInUsers.put(playerID, discordUserID);
        awaitingLogins.remove(discordUserID);
        refreshBotStatus();
    }

    private void handleRegister(final Player p) {
        final String registrationCode = assignRegistrationCodeToPlayer(p);
        p.sendMessage("/register " + registrationCode);
    }

    private void handleLogin(final Player p) throws NotFoundException {
        final String discordID = userRepository.getDiscordID(p.getUniqueId());

        // Make sure user is still part of Discord guild
        try {
            plugin.jda.getGuildById(config.getDiscordBotGuildID()).retrieveMemberById(discordID).complete();
        } catch (Exception ex) {
            return;
        }

        plugin.jda.openPrivateChannelById(discordID).queue(privateChannel -> {
            privateChannel.sendMessage(messages.getDiscordLoginMessage(Bukkit.getName(), p.getAddress().toString())).queue(message -> {
                awaitingLogins.put(discordID, Pair.of(message.getId(), p.getUniqueId()));
                message.addReaction(config.getDiscordButtonsAccept()).queue();
                message.addReaction(config.getDiscordButtonsReject()).queue();
            });
        });
    }

    private synchronized String assignRegistrationCodeToPlayer(final Player p) {
        String registrationCode;
        do {
            registrationCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        } while (discordRegisterCodes.containsKey(registrationCode));

        discordRegisterCodes.put(registrationCode, p.getUniqueId());
        return registrationCode;
    }

    private void refreshBotStatus() {
        if (!config.getDiscordBotEnableActiveStatus()) {
            return;
        }

        plugin.jda.getPresence().setActivity(Activity.playing(String.format("Minecraft %d/%d", loggedInUsers.size(), Bukkit.getMaxPlayers())));
    }
}
