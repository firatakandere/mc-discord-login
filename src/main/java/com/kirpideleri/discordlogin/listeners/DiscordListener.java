package com.kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import com.kirpideleri.discordlogin.exceptions.NotFoundException;
import com.kirpideleri.discordlogin.exceptions.UnregisterUserException;
import com.kirpideleri.discordlogin.utils.IAccountManager;
import com.kirpideleri.discordlogin.utils.IConfig;
import com.kirpideleri.discordlogin.utils.IDiscordCommand;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class DiscordListener extends ListenerAdapter {

    @Inject
    public DiscordListener(final IAccountManager accountManager, final IConfig config) {
        this.config = config;
        this.accountManager = accountManager;
        this.commands = new HashMap<>();
    }

    private final HashMap<String, IDiscordCommand> commands;
    private final IConfig config;
    private final IAccountManager accountManager;

    public void addCommand(final String commandName, final IDiscordCommand command) {
        commands.put(commandName, command);
    }

    @Override
    public void onGuildMessageReceived(final GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return; // Ignore bot messages
        }
        if (!e.getGuild().getId().equals(config.getDiscordBotGuildID())) {
            return; // Make sure guild ids match
        }

        if (e.getChannel().getId().equals(config.getDiscordCommandChannelID())) {
            onMessageToCommandChannel(e);
            return;
        }

        if (e.getChannel().getId().equals(config.getDiscordCommunicationChannelID()))
        {
            onMessageToCommunicationChannel(e);
            return;
        }
    }

    @Override
    public void onPrivateMessageReactionAdd(final PrivateMessageReactionAddEvent e) {
        accountManager.handleUserReaction(e.getUserId(), e.getMessageId(), e.getReactionEmote().getName());
    }

    @Override
    public void onGuildMemberRemove(final GuildMemberRemoveEvent e) {
        if (!e.getGuild().getId().equals(config.getDiscordBotGuildID())) {
            return; // Ignore if from another guild
        }

        // @todo handling error can be better.
        try {
            accountManager.unregisterPlayer(e.getUser().getId());
        } catch (NotFoundException | UnregisterUserException ex) {
            Bukkit.getLogger().log(Level.WARNING, "Could not unregister a player that left Discord guild.", ex);
        }
    }

    private void onMessageToCommandChannel(final GuildMessageReceivedEvent e)
    {
        final String prefix = config.getDiscordCommandPrefix();
        final String message = e.getMessage().getContentDisplay().trim();

        if (!message.startsWith(prefix)) {
            return; // Skip if message does not start with prefix.
        }

        final String[] words = message.split(" ");
        final String commandName = words[0].substring(1);

        if (!commands.containsKey(commandName))
        {
            return; // Ignore undefined commands;
        }

        final String[] args = Arrays.copyOfRange(words, 1, words.length);
        commands.get(commandName).execute(e, args);
    }

    private void onMessageToCommunicationChannel(final GuildMessageReceivedEvent e)
    {
        Bukkit.broadcastMessage(ChatColor.RED + e.getAuthor().getName() + ":" + ChatColor.WHITE + " " + e.getMessage().getContentStripped());
    }
}
