package kirpideleri.discordlogin.utils;

import com.google.inject.Inject;
import kirpideleri.discordlogin.DiscordLoginPlugin;
import kirpideleri.discordlogin.exceptions.NotFoundException;
import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import kirpideleri.discordlogin.repositories.user.IUserRepository;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class AccountManager implements IAccountManager {
    private final Map<String, UUID> discordRegisterCodes;
    private final Map<String, Pair<String, UUID>> awaitingLogins;

    private final Map<UUID, String> loggedInUsers;

    // @todo I really don't like this here.
    @Inject
    private DiscordLoginPlugin plugin;

    @Inject
    private IMessages messages;

    @Inject
    private IConfig config;

    @Inject
    private IUserRepository userRepository;

    public AccountManager() {
        discordRegisterCodes = new HashMap<>();
        loggedInUsers = new HashMap<>(); // playerUUID, discordUserID
        awaitingLogins = new HashMap<>(); // discordUserID, (discordMessageID, playerUUID)
    }

    // @todo any better way?
    public void onEnable() {
        refreshBotStatus();
    }

    public void initializePlayer(final Player p) {
        if (userRepository.isRegistered(p.getUniqueId())) {
            try {
                this.handleLogin(p);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "failed", e);
                // @todo handle this
            }
        } else {
            this.handleRegister(p);
        }
    }

    public void registerPlayer(String registrationCode, String discordUserID) throws RegisterUserException, RegistrationKeyNotFoundException {
        UUID playerID = discordRegisterCodes.getOrDefault(registrationCode, null);

        if (playerID == null) {
            throw new RegistrationKeyNotFoundException();
        }

        this.userRepository.registerUser(playerID, discordUserID);

        // Log user in.
        this.finalizeLogin(discordUserID, playerID);

        // Clear
        discordRegisterCodes.remove(registrationCode);
    }

    public void handleUserReaction(String discordUserID, String discordMessageID, String emote) {
        if (emote.equals(config.getDiscordButtonsReject())) {
            awaitingLogins.remove(discordUserID);
        }
        else if (emote.equals(config.getDiscordButtonsAccept()) && awaitingLogins.containsKey(discordUserID)) {
            Pair<String, UUID> pair = awaitingLogins.get(discordUserID);
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
        String discordID = loggedInUsers.get(p.getUniqueId());
        loggedInUsers.remove(p.getUniqueId());
        awaitingLogins.remove(discordID);
        discordRegisterCodes.remove(discordID);
        refreshBotStatus();
    }

    private void finalizeLogin(String discordUserID, UUID playerID) {
        loggedInUsers.put(playerID, discordUserID);
        awaitingLogins.remove(discordUserID);
        refreshBotStatus();
    }

    private void handleRegister(final Player p) {
        String registrationCode = assignRegistrationCodeToPlayer(p);
        p.sendMessage("/register " + registrationCode);
    }

    private void handleLogin(final Player p) throws NotFoundException {
        String discordID = userRepository.getDiscordID(p.getUniqueId());

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
