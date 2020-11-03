package kirpideleri.discordlogin.utils;

public interface IConfig {
    String GetDiscordBotToken();
    String GetDiscordBotOnlineStatus();
    String GetDiscordBotGuildID();
    String GetDiscordCommandPrefix();
    String GetDiscordCommandChannelID();
}
