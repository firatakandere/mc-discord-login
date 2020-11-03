package kirpideleri.discordlogin.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Messages implements IMessages {
    private YamlConfiguration ymlConfig;

    private final String DISCORD_COMMON_INVALID_ARGUMENT_COUNT = "Discord.Common.InvalidArgumentCount";

    private final String DISCORD_REGISTRATION_SUCCESS = "Discord.Registration.Success";
    private final String DISCORD_REGISTRATION_FAILURE = "Discord.Registration.Failure";
    private final String DISCORD_REGISTRATION_INVALID_KEY = "Discord.Registration.InvalidKey";

    public Messages() {
        // @todo generate with default values if does not exist
        File messagesFile = new File("plugins/DiscordLogin/Messages.yml");
        this.ymlConfig = YamlConfiguration.loadConfiguration(messagesFile);
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
}
