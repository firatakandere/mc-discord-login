package kirpideleri.discordlogin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import kirpideleri.discordlogin.commands.discord.RegisterCommand;
import kirpideleri.discordlogin.commands.discord.WhoIsOnlineCommand;
import kirpideleri.discordlogin.listeners.DiscordListener;
import kirpideleri.discordlogin.listeners.PlayerJoinListener;
import kirpideleri.discordlogin.listeners.PreventionListener;
import kirpideleri.discordlogin.utils.IConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public class DiscordLoginPlugin extends JavaPlugin
{
    public JDA jda;

    @Inject
    private IConfig config;

    @Inject
    DiscordListener discordListener;

    @Inject
    PlayerJoinListener playerJoinListener;

    @Inject
    RegisterCommand registerCommand;

    @Inject
    PreventionListener preventionListener;

    @Inject
    WhoIsOnlineCommand whoisonlineCommand;

    @Override
    public void onEnable() {
        BinderModule module = new BinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        this.connectToDiscord();
        this.registerEvents();
        getLogger().info("DiscordLogin is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("DiscordLogin is disabled.");

        if (jda != null) {
            jda.shutdownNow();
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(this.preventionListener, this);
        Bukkit.getPluginManager().registerEvents(this.playerJoinListener, this);

        discordListener.addCommand("register", this.registerCommand);
        discordListener.addCommand("whoisonline", this.whoisonlineCommand);
    }

    private void connectToDiscord() {
        final JDABuilder jdaBuilder = JDABuilder.createDefault(this.config.getDiscordBotToken());

        try {
            jdaBuilder.setStatus(OnlineStatus.valueOf(this.config.getDiscordBotOnlineStatus()));
        } catch (IllegalArgumentException e) {
            jdaBuilder.setStatus(OnlineStatus.ONLINE);
        }

        jdaBuilder.setAutoReconnect(true);

        try {
            jda = jdaBuilder.build();
            jda.awaitReady();

            jda.addEventListener(this.discordListener);
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Connection to Discord has failed.", ex.getCause());
        }
    }
}