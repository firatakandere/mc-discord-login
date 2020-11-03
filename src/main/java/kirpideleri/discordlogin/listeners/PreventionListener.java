package kirpideleri.discordlogin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class PreventionListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerFish(final PlayerFishEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerShear(final PlayerShearEntityEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onSignChange(final SignChangeEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onBedEnter(final PlayerBedEnterEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onDropItem(final PlayerDropItemEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onInteractEntity(final PlayerInteractEntityEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
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

        this.showMessage(p);
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onInventoryOpen(final InventoryOpenEvent e) {
        this.showMessage((Player) e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onItemConsume(final PlayerItemConsumeEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPlayerInteract(final PlayerInteractEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onPickupItem(final EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        this.showMessage((Player) e.getEntity());
        e.setCancelled(true);
    }

    // @todo check command whitelist
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onCommand(final PlayerCommandPreprocessEvent e) {
        this.showMessage(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onMove(final PlayerMoveEvent e) {
        e.setCancelled(true);
    }

    private void showMessage(Player p) {

    }
}
