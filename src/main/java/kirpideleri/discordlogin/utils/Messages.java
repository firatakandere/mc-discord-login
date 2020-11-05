package kirpideleri.discordlogin.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class Messages implements IMessages {
    private YamlConfiguration ymlConfig;

    private final String DISCORD_COMMON_INVALID_ARGUMENT_COUNT = "Discord.Common.InvalidArgumentCount";

    private final String DISCORD_REGISTRATION_SUCCESS = "Discord.Registration.Success";
    private final String DISCORD_REGISTRATION_FAILURE = "Discord.Registration.Failure";
    private final String DISCORD_REGISTRATION_INVALID_KEY = "Discord.Registration.InvalidKey";

    private final String DISCORD_LOGIN_MESSAGE = "Discord.Login.Message";

    public Messages() {
        File messagesFile = new File("plugins/DiscordLogin/Messages.yml");
        ymlConfig = YamlConfiguration.loadConfiguration(messagesFile);

        ymlConfig.addDefault(DISCORD_COMMON_INVALID_ARGUMENT_COUNT, "Command is expected to have {{count}} argument(s).");
        ymlConfig.addDefault(DISCORD_REGISTRATION_SUCCESS, "{{username}} has been registered successfully.");
        ymlConfig.addDefault(DISCORD_REGISTRATION_FAILURE, "Registration failed, please contact with server admin.");
        ymlConfig.addDefault(DISCORD_REGISTRATION_INVALID_KEY, "Registration key {{key}} is invalid.");
        ymlConfig.addDefault(DISCORD_LOGIN_MESSAGE, "You have just joined {{servername}} with the ip {{ip}}. Use emotes to approve or deny the login.");
        ymlConfig.options().copyDefaults(true);
        try {
            ymlConfig.save(messagesFile);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not create messages file.", ex);
        }
    }

    public String getDiscordRegistrationFailure() {
        return ymlConfig.getString(DISCORD_REGISTRATION_FAILURE);
    }

    public String getDiscordRegistrationSuccess(String discordUserID) {
        return ymlConfig.getString(DISCORD_REGISTRATION_SUCCESS).replace("{{username}}", String.format("<@%s>", discordUserID));
    }

    public String getDiscordRegistrationInvalidKey(String registrationKey) {
        return ymlConfig.getString(DISCORD_REGISTRATION_INVALID_KEY).replace("{{key}}", registrationKey);
    }

    public String getDiscordCommonInvalidArgumentCount(int expectedArgumentCount) {
        return ymlConfig.getString(DISCORD_COMMON_INVALID_ARGUMENT_COUNT).replace("{{count}}", String.valueOf(expectedArgumentCount));
    }

    public String getDiscordLoginMessage(String serverName, String joinedIpAddress) {
        return ymlConfig.getString(DISCORD_LOGIN_MESSAGE)
                .replace("{{servername}}", serverName)
                .replace("{{ip}}", joinedIpAddress);
    }
}
