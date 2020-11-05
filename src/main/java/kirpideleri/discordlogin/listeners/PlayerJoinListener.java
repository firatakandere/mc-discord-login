package kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import kirpideleri.discordlogin.utils.IAccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @Inject
    IAccountManager accountManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(final PlayerJoinEvent e) {
        this.accountManager.initializePlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerQuit(PlayerQuitEvent e) {
        this.accountManager.handlePlayerQuit(e.getPlayer());
    }
}
