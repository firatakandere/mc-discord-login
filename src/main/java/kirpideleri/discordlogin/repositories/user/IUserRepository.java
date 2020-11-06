package kirpideleri.discordlogin.repositories.user;

import kirpideleri.discordlogin.exceptions.NotFoundException;
import kirpideleri.discordlogin.exceptions.RegisterUserException;

import java.util.UUID;

public interface IUserRepository {
    boolean isRegistered(final UUID userID);
    void registerUser(final UUID userID, final String discordID) throws RegisterUserException;
    String getDiscordID(final UUID playerID) throws NotFoundException;
}
