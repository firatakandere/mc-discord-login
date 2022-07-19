package com.kirpideleri.discordlogin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kirpideleri.discordlogin.commands.discord.RegisterCommand;
import com.kirpideleri.discordlogin.commands.discord.UnregisterCommand;
import com.kirpideleri.discordlogin.commands.discord.WhoIsOnlineCommand;
import com.kirpideleri.discordlogin.utils.AccountManager;
import com.kirpideleri.discordlogin.utils.IConfig;
import com.kirpideleri.discordlogin.listeners.DiscordListener;
import com.kirpideleri.discordlogin.listeners.PlayerJoinListener;
import com.kirpideleri.discordlogin.listeners.PreventionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
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
    UnregisterCommand unregisterCommand;

    @Inject
    PreventionListener preventionListener;

    @Inject
    WhoIsOnlineCommand whoisonlineCommand;

    @Inject
    AccountManager accountManager;

    @Override
    public void onEnable() {
        BinderModule module = new BinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        this.connectToDiscord();
        this.registerEvents();

        accountManager.onEnable();
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
        discordListener.addCommand("unregister", this.unregisterCommand);
        discordListener.addCommand("whoisonline", this.whoisonlineCommand);
    }

    private void connectToDiscord() {
        final JDABuilder jdaBuilder = JDABuilder.create(this.config.getDiscordBotToken(), GatewayIntent.GUILD_MEMBERS);

        try {
            jdaBuilder.setStatus(OnlineStatus.valueOf(this.config.getDiscordBotOnlineStatus()));
        } catch (IllegalArgumentException e) {
            jdaBuilder.setStatus(OnlineStatus.ONLINE);
        }

        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES);
        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS);

        try {
            jda = jdaBuilder.build();
            jda.awaitReady();

            jda.addEventListener(this.discordListener);
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Connection to Discord has failed.", ex.getCause());
        }
    }
}