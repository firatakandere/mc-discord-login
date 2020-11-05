package kirpideleri.discordlogin.utils;

import java.util.Set;

public interface IConfig {
    String GetDiscordBotToken();
    String GetDiscordBotOnlineStatus();
    String GetDiscordBotGuildID();
    String GetDiscordCommandPrefix();
    String GetDiscordCommandChannelID();
    String GetDiscordButtonsAccept();
    String GetDiscordButtonsReject();
    Set<String> GetServerCommandWhitelist();
}
