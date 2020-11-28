package kirpideleri.discordlogin.repositories.user;

import kirpideleri.discordlogin.exceptions.NotFoundException;
import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.UnregisterUserException;

import java.util.UUID;

public interface IUserRepository {
    boolean isRegistered(final UUID userID);
    void registerUser(final UUID userID, final String discordID) throws RegisterUserException;
    void unregisterUser(final String discordID) throws UnregisterUserException, NotFoundException;
    String getDiscordID(final UUID playerID) throws NotFoundException;
}
