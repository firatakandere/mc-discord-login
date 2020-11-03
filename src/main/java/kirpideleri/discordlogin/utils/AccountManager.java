package kirpideleri.discordlogin.utils;

import com.google.inject.Inject;
import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import kirpideleri.discordlogin.repositories.user.IUserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AccountManager implements IAccountManager {
    private HashMap<String, UUID> discordRegisterCodes;

    @Inject
    private IUserRepository userRepository;

    // @todo Set timeout for registration keys and clean them periodically
    public AccountManager() {
        discordRegisterCodes = new HashMap<String, UUID>();
    }

    public void InitializePlayer(final Player p) {
        if (userRepository.isRegistered(p.getUniqueId())) {
            // @todo send login things
        } else {
            this.HandleRegister(p);
        }
    }

    public void registerPlayer(String registrationCode, String discordUserID) throws RegisterUserException, RegistrationKeyNotFoundException {
        if (!discordRegisterCodes.containsKey(registrationCode)) {
            throw new RegistrationKeyNotFoundException();
        }

        this.userRepository.registerUser(discordRegisterCodes.get(registrationCode), discordUserID);
        discordRegisterCodes.remove(registrationCode);
    }

    private void HandleRegister(final Player p) {
        String registrationCode = assignRegistrationCodeToPlayer(p);
        p.sendMessage("/register " + registrationCode);
    }

    private synchronized String assignRegistrationCodeToPlayer(final Player p) {
        String registrationCode;
        do {
            registrationCode = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        } while (discordRegisterCodes.containsKey(registrationCode));

        discordRegisterCodes.put(registrationCode, p.getUniqueId());
        return registrationCode;
    }
}
