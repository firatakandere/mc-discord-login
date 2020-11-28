package kirpideleri.discordlogin.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class Messages implements IMessages {
    private final YamlConfiguration ymlConfig;

    private final String DISCORD_COMMON_INVALID_ARGUMENT_COUNT = "Discord.Common.InvalidArgumentCount";

    private final String DISCORD_REGISTRATION_SUCCESS = "Discord.Registration.Success";
    private final String DISCORD_REGISTRATION_FAILURE = "Discord.Registration.Failure";
    private final String DISCORD_REGISTRATION_INVALID_KEY = "Discord.Registration.InvalidKey";

    private final String DISCORD_UNREGISTRATION_SUCCESS = "Discord.Unregistration.Success";
    private final String DISCORD_UNREGISTRATION_FAILURE = "Discord.Unregistration.Failure";
    private final String DISCORD_UNREGISTRATION_NOT_FOUND = "Discord.Unregistration.NotFound";

    private final String DISCORD_NO_ONLINE_USERS = "Discord.NoOnlineUsers";

    private final String DISCORD_LOGIN_MESSAGE = "Discord.Login.Message";

    private final String SERVER_TIMEOUT_FAILURE = "Minecraft.Timeout.Failure";

    public Messages() {
        final File messagesFile = new File("plugins/DiscordLogin/Messages.yml");
        ymlConfig = YamlConfiguration.loadConfiguration(messagesFile);

        ymlConfig.addDefault(DISCORD_COMMON_INVALID_ARGUMENT_COUNT, "Command is expected to have {{count}} argument(s).");
        ymlConfig.addDefault(DISCORD_REGISTRATION_SUCCESS, "{{username}} has been registered successfully.");
        ymlConfig.addDefault(DISCORD_REGISTRATION_FAILURE, "Registration failed, please contact with server admin.");
        ymlConfig.addDefault(DISCORD_REGISTRATION_INVALID_KEY, "Registration key {{key}} is invalid.");
        ymlConfig.addDefault(DISCORD_LOGIN_MESSAGE, "You have just joined {{servername}} with the ip {{ip}}. Use emotes to approve or deny the login.");
        ymlConfig.addDefault(DISCORD_NO_ONLINE_USERS, "No online users.");
        ymlConfig.addDefault(DISCORD_UNREGISTRATION_SUCCESS, "{{username}} has been unregistered successfully.");
        ymlConfig.addDefault(DISCORD_UNREGISTRATION_FAILURE, "Unregister has failed. Please contact server admin.");
        ymlConfig.addDefault(DISCORD_UNREGISTRATION_NOT_FOUND, "User registration for {{username}} has not been found.");
        ymlConfig.addDefault(SERVER_TIMEOUT_FAILURE, "You have failed to register/login within allowed timeframe.");
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

    public String getDiscordRegistrationSuccess(final String discordUserID) {
        return ymlConfig.getString(DISCORD_REGISTRATION_SUCCESS).replace("{{username}}", String.format("<@%s>", discordUserID));
    }

    public String getDiscordRegistrationInvalidKey(final String registrationKey) {
        return ymlConfig.getString(DISCORD_REGISTRATION_INVALID_KEY).replace("{{key}}", registrationKey);
    }

    public String getDiscordUnregistrationSuccess(String discordUserID) {
        return ymlConfig.getString(DISCORD_UNREGISTRATION_SUCCESS).replace("{{username}}", String.format("<@%s>", discordUserID));
    }

    public String getDiscordUnregistrationNotFound(String discordUserID) {
        return ymlConfig.getString(DISCORD_UNREGISTRATION_NOT_FOUND).replace("{{username}}", String.format("<@%s>", discordUserID));
    }

    public String getDiscordUnregistrationFailure() {
        return ymlConfig.getString(DISCORD_UNREGISTRATION_FAILURE);
    }

    public String getDiscordCommonInvalidArgumentCount(final int expectedArgumentCount) {
        return ymlConfig.getString(DISCORD_COMMON_INVALID_ARGUMENT_COUNT).replace("{{count}}", String.valueOf(expectedArgumentCount));
    }

    public String getDiscordLoginMessage(final String serverName, final String joinedIpAddress) {
        return ymlConfig.getString(DISCORD_LOGIN_MESSAGE)
                .replace("{{servername}}", serverName)
                .replace("{{ip}}", joinedIpAddress);
    }

    public String getDiscordNoOnlineUsers() { return ymlConfig.getString(DISCORD_NO_ONLINE_USERS); }

    public String getServerTimeoutFailure() { return ymlConfig.getString(SERVER_TIMEOUT_FAILURE); }
}
