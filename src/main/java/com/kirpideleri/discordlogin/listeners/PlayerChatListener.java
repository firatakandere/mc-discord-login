package com.kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import com.kirpideleri.discordlogin.DiscordLoginPlugin;
import com.kirpideleri.discordlogin.utils.IConfig;
import net.dv8tion.jda.api.entities.ChannelType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class PlayerChatListener implements Listener {

    @Inject
    public PlayerChatListener(final DiscordLoginPlugin plugin, final IConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    private final DiscordLoginPlugin plugin;
    private final IConfig config;

    @EventHandler
    private void onPlayerChat(final AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("/")) {
            return; // ignore commands
        }

        plugin.jda
                .getTextChannelById(config.getDiscordCommunicationChannelID())
                .sendMessage(e.getPlayer().getDisplayName() + ": " + e.getMessage())
                .queue();
    }
}
