package kirpideleri.discordlogin.utils;

import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface IAccountManager {
    void initializePlayer(final Player p);
    void registerPlayer(final String registrationCode, final String discordUserID) throws RegisterUserException, RegistrationKeyNotFoundException;
    void handleUserReaction(final String discordUserID, final String discordMessageID, final String emote);
    void handlePlayerQuit(final Player p);
    boolean isLoggedIn(final Player p);
    Map<UUID, String> getLoggedInUsers();
    void onEnable();
}
