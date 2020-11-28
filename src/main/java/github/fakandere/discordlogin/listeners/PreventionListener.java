package github.fakandere.discordlogin.listeners;

import com.google.inject.Inject;
import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.utils.IAccountManager;
import github.fakandere.discordlogin.utils.IConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreventionListener implements Listener {

    @Inject
    public PreventionListener(final IAccountManager accountManager, final IConfig config) {
        this.accountManager = accountManager;
        this.config = config;
    }

    private final IAccountManager accountManager;
    private final IConfig config;


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerFish(final PlayerFishEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerShear(final PlayerShearEntityEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onSignChange(final SignChangeEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onBedEnter(final PlayerBedEnterEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onDropItem(final PlayerDropItemEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onInteractEntity(final PlayerInteractEntityEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
        Player p;
        if (e.getEntity() instanceof Player) {
            p = (Player)e.getEntity();
        }
        else if (e.getDamager() instanceof Player) {
            p = (Player)e.getDamager();
        }
        else {
            return;
        }

        if (!accountManager.isLoggedIn(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onInventoryOpen(final InventoryOpenEvent e) {
        if (!accountManager.isLoggedIn((Player) e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onItemConsume(final PlayerItemConsumeEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerInteract(final PlayerInteractEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPickupItem(final EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (!accountManager.isLoggedIn((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onCommand(final PlayerCommandPreprocessEvent e) {
        try {
            final String command = extractCommand(e.getMessage());
            if (config.getServerCommandWhitelist().contains(command)) {
                return;
            }
        } catch (NotFoundException ex) {
            // just ignore
        }

        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onMove(final PlayerMoveEvent e) {
        if (!accountManager.isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    private String extractCommand(final String str) throws NotFoundException {
        final String pattern = "^/(\\S+)";
        final Pattern r = Pattern.compile(pattern);
        final Matcher m = r.matcher(str);
        if (m.find() && m.groupCount() == 1) {
            return m.group(1);
        }else {
            throw new NotFoundException("Could not extract command");
        }
    }
}
