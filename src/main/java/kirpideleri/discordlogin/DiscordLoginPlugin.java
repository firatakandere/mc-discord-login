package kirpideleri.discordlogin;

import kirpideleri.discordlogin.listeners.PreventionListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordLoginPlugin extends JavaPlugin
{
    @Override
    public void onEnable() {
        this.registerEvents();
        getLogger().info("DiscordLogin is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("DiscordLogin is disabled.");
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PreventionListener(), this);
    }
}