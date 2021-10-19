package com.kirpideleri.discordlogin.repositories.user;

import com.kirpideleri.discordlogin.exceptions.NotFoundException;
import com.kirpideleri.discordlogin.exceptions.RegisterUserException;
import com.kirpideleri.discordlogin.exceptions.UnregisterUserException;

import java.util.UUID;

public interface IUserRepository {
    boolean isRegistered(final UUID userID);
    void registerUser(final UUID userID, final String discordID) throws RegisterUserException;
    void unregisterUser(final String discordID) throws UnregisterUserException, NotFoundException;
    String getDiscordID(final UUID playerID) throws NotFoundException;
}
