package kirpideleri.discordlogin.listeners;

import com.google.inject.Inject;
import kirpideleri.discordlogin.utils.IAccountManager;
import kirpideleri.discordlogin.utils.IConfig;
import kirpideleri.discordlogin.utils.IDiscordCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;

public class DiscordListener extends ListenerAdapter {
    private HashMap<String, IDiscordCommand> commands;

    public DiscordListener() {
        this.commands = new HashMap<>();
    }

    @Inject
    private IConfig config;

    @Inject
    private IAccountManager accountManager;

    public void addCommand(final String commandName, IDiscordCommand command) {
        this.commands.put(commandName, command);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return; // Ignore bot messages
        }
        if (!e.getGuild().getId().equals(this.config.GetDiscordBotGuildID())) {
            return; // Make sure guild ids match
        }

        if (!e.getChannel().getId().equals(this.config.GetDiscordCommandChannelID()));

        final String prefix = this.config.GetDiscordCommandPrefix();
        String message = e.getMessage().getContentDisplay().trim();

        if (!message.startsWith(prefix)) {
            return; // Skip if message does not start with prefix.
        }

        final String[] words = message.split(" ");
        final String commandName = words[0].substring(1);

        if (!this.commands.containsKey(commandName))
        {
            return; // Ignore undefined commands;
        }

        final String[] args = Arrays.copyOfRange(words, 1, words.length);
        this.commands.get(commandName).execute(e, args);


        super.onGuildMessageReceived(e);
    }

    @Override
    public void onPrivateMessageReactionAdd(@Nonnull PrivateMessageReactionAddEvent e) {
        accountManager.handleUserReaction(e.getUserId(), e.getMessageId(), e.getReactionEmote().getName());

        super.onPrivateMessageReactionAdd(e);
    }
}
