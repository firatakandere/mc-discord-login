package kirpideleri.discordlogin.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config implements IConfig {
    private YamlConfiguration ymlConfig;

    private final String DISCORD_BOT_TOKEN = "Discord.Bot.Token";
    private final String DISCORD_BOT_ONLINE_STATUS = "Discord.Bot.OnlineStatus";
    private final String DISCORD_BOT_GUILD_ID = "Discord.GuildID";
    private final String DISCORD_COMMAND_PREFIX = "Discord.CommandPrefix";
    private final String DISCORD_COMMAND_CHANNEL_ID = "Discord.CommandChannelId";
    private final String DISCORD_BUTTONS_ACCEPT = "Discord.Buttons.Accept";
    private final String DISCORD_BUTTONS_REJECT = "Discord.Buttons.Reject";

    private final String SERVER_COMMAND_WHITELIST = "Server.CommandWhitelist";

    public Config() {
        File configFile = new File("plugins/DiscordLogin/Settings.yml");
        ymlConfig = YamlConfiguration.loadConfiguration(configFile);

        ymlConfig.addDefault(DISCORD_BOT_TOKEN, "");
        ymlConfig.addDefault(DISCORD_BOT_ONLINE_STATUS, "ONLINE");
        ymlConfig.addDefault(DISCORD_BOT_GUILD_ID, "");
        ymlConfig.addDefault(DISCORD_COMMAND_PREFIX, "/");
        ymlConfig.addDefault(DISCORD_COMMAND_CHANNEL_ID, "");
        ymlConfig.addDefault(DISCORD_BUTTONS_ACCEPT, "✅");
        ymlConfig.addDefault(DISCORD_BUTTONS_REJECT, "❌");
        ymlConfig.options().copyDefaults(true);

        try {
            ymlConfig.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save configuration file", e);
        }
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

    public String GetDiscordButtonsAccept() { return this.ymlConfig.getString(DISCORD_BUTTONS_ACCEPT); }

    public String GetDiscordButtonsReject() { return this.ymlConfig.getString(DISCORD_BUTTONS_REJECT); }

    public Set<String> GetServerCommandWhitelist() { return new HashSet<>(this.ymlConfig.getStringList(SERVER_COMMAND_WHITELIST)); }
}
