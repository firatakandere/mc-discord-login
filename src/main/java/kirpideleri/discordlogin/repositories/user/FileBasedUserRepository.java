package kirpideleri.discordlogin.repositories.user;

import kirpideleri.discordlogin.exceptions.RegisterUserException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class FileBasedUserRepository implements IUserRepository {
    private final String path = "plugins/DiscordLogin/Accounts";

    public FileBasedUserRepository() {
        new File(path).mkdirs();
    }

    @Override
    public boolean isRegistered(UUID userID) {
        return (new File(this.getUserAccountFilePath(userID))).exists();
    }

    @Override
    public void registerUser(UUID userID, String discordID) throws RegisterUserException {
        File file = new File(this.getUserAccountFilePath(userID));
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not create file for user registration", ex);
            throw new RegisterUserException("Could not create file for user registration", ex);
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set("DiscordLogin.DiscordID", discordID);
        try {
            yml.save(file);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save into user file.", ex);
            throw new RegisterUserException("Could not save into user file", ex);
        }
    }

    private String getUserAccountFilePath(UUID userID) {
        return path + "/" + userID + ".yml";
    }
}
