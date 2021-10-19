package com.kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import com.kirpideleri.discordlogin.utils.IAccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @Inject
    public PlayerJoinListener(final IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    private final IAccountManager accountManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(final PlayerJoinEvent e) {
        accountManager.initializePlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerQuit(final PlayerQuitEvent e) {
        accountManager.handlePlayerQuit(e.getPlayer());
    }
}
