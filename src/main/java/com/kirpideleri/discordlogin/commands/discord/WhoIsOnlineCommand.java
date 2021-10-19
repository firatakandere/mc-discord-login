package com.kirpideleri.discordlogin.commands.discord;

import com.google.inject.Inject;
import com.kirpideleri.discordlogin.utils.IAccountManager;
import com.kirpideleri.discordlogin.utils.IDiscordCommand;
import com.kirpideleri.discordlogin.utils.IMessages;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WhoIsOnlineCommand implements IDiscordCommand {

    @Inject
    WhoIsOnlineCommand(final IAccountManager accountManager, final IMessages messages) {
        this.accountManager = accountManager;
        this.messages = messages;
    }

    private final IAccountManager accountManager;
    private final IMessages messages;

    @Override
    public void execute(final GuildMessageReceivedEvent e, final String[] args)
    {
        if (args.length != 0) {
            e.getChannel().sendMessage(messages.getDiscordCommonInvalidArgumentCount(0)).queue();
            return;
        }

        final Map<UUID, String> loggedInUsers = accountManager.getLoggedInUsers();

        if (loggedInUsers.isEmpty()) {
            e.getChannel().sendMessage(messages.getDiscordNoOnlineUsers()).queue();
            return;
        }

        final List<String> onlinePlayerNames = new ArrayList<>();

        for (UUID playerID : loggedInUsers.keySet()) {
            try {
                onlinePlayerNames.add(Bukkit.getPlayer(playerID).getDisplayName());
            } catch (Exception ex) {
                // ignore
            }
        }

        e.getChannel().sendMessage(String.join(", ", onlinePlayerNames)).queue();
     }
}
