package kirpideleri.discordlogin.utils;

import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import org.bukkit.entity.Player;

public interface IAccountManager {
    void InitializePlayer(final Player p);
    void registerPlayer(String registrationCode, String discordUserID) throws RegisterUserException, RegistrationKeyNotFoundException;
}
