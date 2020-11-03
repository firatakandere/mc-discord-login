package kirpideleri.discordlogin.repositories.user;

import kirpideleri.discordlogin.exceptions.RegisterUserException;

import java.util.UUID;

public interface IUserRepository {
    boolean isRegistered(UUID userID);
    void registerUser(UUID userID, String discordID) throws RegisterUserException;
}
