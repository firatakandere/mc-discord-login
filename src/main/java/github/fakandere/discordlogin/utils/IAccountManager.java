package github.fakandere.discordlogin.utils;

import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.exceptions.RegisterUserException;
import github.fakandere.discordlogin.exceptions.UnregisterUserException;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface IAccountManager {
    void initializePlayer(final Player p);
    void registerPlayer(final String registrationCode, final String discordUserID) throws RegisterUserException, NotFoundException;
    void unregisterPlayer(final String discordUserID) throws NotFoundException, UnregisterUserException;
    void handleUserReaction(final String discordUserID, final String discordMessageID, final String emote);
    void handlePlayerQuit(final Player p);
    boolean isLoggedIn(final Player p);
    Map<UUID, String> getLoggedInUsers();
    void onEnable();
}
