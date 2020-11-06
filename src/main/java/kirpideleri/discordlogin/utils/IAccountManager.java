package kirpideleri.discordlogin.utils;

import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface IAccountManager {
    void initializePlayer(final Player p);
    void registerPlayer(String registrationCode, String discordUserID) throws RegisterUserException, RegistrationKeyNotFoundException;
    void handleUserReaction(String discordUserID, String discordMessageID, String emote);
    void handlePlayerQuit(final Player p);
    boolean isLoggedIn(final Player p);
    Map<UUID, String> getLoggedInUsers();
    void onEnable();
}
