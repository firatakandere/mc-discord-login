package com.kirpideleri.discordlogin.utils;

import java.util.Set;

public interface IConfig {
    String getDiscordBotToken();
    String getDiscordBotOnlineStatus();
    String getDiscordBotGuildID();
    String getDiscordCommandPrefix();
    String getDiscordCommandChannelID();
    String getDiscordCommunicationChannelID();
    String getDiscordButtonsAccept();
    String getDiscordButtonsReject();
    Set<String> getServerCommandWhitelist();
    boolean getDiscordBotEnableActiveStatus();
    int getServerTimeoutInSeconds();
}
