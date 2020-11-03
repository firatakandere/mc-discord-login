package kirpideleri.discordlogin.utils;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config implements IConfig {
    private YamlConfiguration ymlConfig;

    private final String DISCORD_BOT_TOKEN = "Discord.Bot.Token";
    private final String DISCORD_BOT_ONLINE_STATUS = "Discord.Bot.OnlineStatus";
    private final String DISCORD_BOT_GUILD_ID = "Discord.GuildID";
    private final String DISCORD_COMMAND_PREFIX = "Discord.CommandPrefix";
    private final String DISCORD_COMMAND_CHANNEL_ID = "Discord.CommandChannelId";

    public Config() {
        // @todo generate with default values if does not exist
        File configFile = new File("plugins/DiscordLogin/Settings.yml");
        this.ymlConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    public String GetDiscordBotToken() {
        return this.ymlConfig.getString(DISCORD_BOT_TOKEN);
    }

    public String GetDiscordBotOnlineStatus() {
        return this.ymlConfig.getString(DISCORD_BOT_ONLINE_STATUS);
    }

    public String GetDiscordBotGuildID() {
        return this.ymlConfig.getString(DISCORD_BOT_GUILD_ID);
    }

    public String GetDiscordCommandPrefix() { return this.ymlConfig.getString(DISCORD_COMMAND_PREFIX); }

    public String GetDiscordCommandChannelID() { return this.ymlConfig.getString(DISCORD_COMMAND_CHANNEL_ID); }
}
