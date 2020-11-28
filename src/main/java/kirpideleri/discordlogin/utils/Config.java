package kirpideleri.discordlogin.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config implements IConfig {
    private final YamlConfiguration ymlConfig;

    private final String DISCORD_BOT_TOKEN = "Discord.Bot.Token";
    private final String DISCORD_BOT_ONLINE_STATUS = "Discord.Bot.OnlineStatus";
    private final String DISCORD_BOT_GUILD_ID = "Discord.GuildID";
    private final String DISCORD_BOT_ENABLE_ACTIVE_STATUS = "Discord.Bot.EnableActiveStatus";
    private final String DISCORD_COMMAND_PREFIX = "Discord.CommandPrefix";
    private final String DISCORD_COMMAND_CHANNEL_ID = "Discord.CommandChannelId";
    private final String DISCORD_BUTTONS_ACCEPT = "Discord.Buttons.Accept";
    private final String DISCORD_BUTTONS_REJECT = "Discord.Buttons.Reject";

    private final String SERVER_COMMAND_WHITELIST = "Server.CommandWhitelist";
    private final String SERVER_TIMEOUT_IN_SECONDS = "Server.TimeoutInSeconds";

    public Config() {
        final File configFile = new File("plugins/DiscordLogin/Settings.yml");
        ymlConfig = YamlConfiguration.loadConfiguration(configFile);

        ymlConfig.addDefault(DISCORD_BOT_TOKEN, "");
        ymlConfig.addDefault(DISCORD_BOT_ONLINE_STATUS, "ONLINE");
        ymlConfig.addDefault(DISCORD_BOT_GUILD_ID, "");
        ymlConfig.addDefault(DISCORD_COMMAND_PREFIX, "/");
        ymlConfig.addDefault(DISCORD_COMMAND_CHANNEL_ID, "");
        ymlConfig.addDefault(DISCORD_BUTTONS_ACCEPT, "✅");
        ymlConfig.addDefault(DISCORD_BUTTONS_REJECT, "❌");
        ymlConfig.addDefault(DISCORD_BOT_ENABLE_ACTIVE_STATUS, true);
        ymlConfig.addDefault(SERVER_TIMEOUT_IN_SECONDS, 20);
        ymlConfig.options().copyDefaults(true);

        try {
            ymlConfig.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save configuration file", e);
        }
    }

    public String getDiscordBotToken() {
        return ymlConfig.getString(DISCORD_BOT_TOKEN);
    }

    public String getDiscordBotOnlineStatus() {
        return ymlConfig.getString(DISCORD_BOT_ONLINE_STATUS);
    }

    public String getDiscordBotGuildID() {
        return ymlConfig.getString(DISCORD_BOT_GUILD_ID);
    }

    public String getDiscordCommandPrefix() { return ymlConfig.getString(DISCORD_COMMAND_PREFIX); }

    public String getDiscordCommandChannelID() { return ymlConfig.getString(DISCORD_COMMAND_CHANNEL_ID); }

    public String getDiscordButtonsAccept() { return ymlConfig.getString(DISCORD_BUTTONS_ACCEPT); }

    public String getDiscordButtonsReject() { return ymlConfig.getString(DISCORD_BUTTONS_REJECT); }

    public Set<String> getServerCommandWhitelist() { return new HashSet<>(ymlConfig.getStringList(SERVER_COMMAND_WHITELIST)); }

    public boolean getDiscordBotEnableActiveStatus() { return ymlConfig.getBoolean(DISCORD_BOT_ENABLE_ACTIVE_STATUS); }

    public int getServerTimeoutInSeconds() { return ymlConfig.getInt(SERVER_TIMEOUT_IN_SECONDS); }
}
