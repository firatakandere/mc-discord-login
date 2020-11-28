package github.fakandere.discordlogin.repositories.user;

import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.exceptions.RegisterUserException;
import github.fakandere.discordlogin.exceptions.UnregisterUserException;

import java.util.UUID;

public interface IUserRepository {
    boolean isRegistered(final UUID userID);
    void registerUser(final UUID userID, final String discordID) throws RegisterUserException;
    void unregisterUser(final String discordID) throws UnregisterUserException, NotFoundException;
    String getDiscordID(final UUID playerID) throws NotFoundException;
}
