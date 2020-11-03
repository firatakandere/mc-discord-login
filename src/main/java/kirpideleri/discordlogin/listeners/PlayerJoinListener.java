package kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import kirpideleri.discordlogin.utils.IAccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @Inject
    IAccountManager accountManager;

    @EventHandler
    private void onJoin(final PlayerJoinEvent e) {
        this.accountManager.InitializePlayer(e.getPlayer());
    }
}
