package kirpideleri.discordlogin.repositories.user;

import kirpideleri.discordlogin.exceptions.NotFoundException;
import kirpideleri.discordlogin.exceptions.RegisterUserException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class FileBasedUserRepository implements IUserRepository {
    private final String path = "plugins/DiscordLogin/Accounts";

    private final String DISCORD_ID = "DiscordLogin.DiscordID";

    public FileBasedUserRepository() {
        new File(path).mkdirs();
    }

    public boolean isRegistered(final UUID userID) {
        return (new File(this.getUserAccountFilePath(userID))).exists();
    }

    public void registerUser(final UUID userID, final String discordID) throws RegisterUserException {
        final File file = new File(this.getUserAccountFilePath(userID));
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not create file for user registration", ex);
            throw new RegisterUserException("Could not create file for user registration", ex);
        }

        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set(DISCORD_ID, discordID);
        try {
            yml.save(file);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save into user file.", ex);
            throw new RegisterUserException("Could not save into user file", ex);
        }
    }

    public String getDiscordID(final UUID playerID) throws NotFoundException {
        final File file = new File(this.getUserAccountFilePath(playerID));
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        final String discordID = yml.getString(DISCORD_ID);
        if (discordID == null) {
            throw new NotFoundException("Discord ID is not available for this user.");
        }

        return discordID;
    }

    private String getUserAccountFilePath(UUID userID) {
        return path + "/" + userID + ".yml";
    }
}
