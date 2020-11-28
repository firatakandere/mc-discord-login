package github.fakandere.discordlogin.commands.discord;

import com.google.inject.Inject;
import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.exceptions.UnregisterUserException;
import github.fakandere.discordlogin.utils.IAccountManager;
import github.fakandere.discordlogin.utils.IDiscordCommand;
import github.fakandere.discordlogin.utils.IMessages;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UnregisterCommand implements IDiscordCommand {

    @Inject
    public UnregisterCommand(final IAccountManager accountManager, final IMessages messages) {
        this.accountManager = accountManager;
        this.messages = messages;
    }

    private final IAccountManager accountManager;
    private final IMessages messages;

    @Override
    public void execute(final GuildMessageReceivedEvent e, final String[] args) {
        if (args.length != 0) {
            e.getChannel().sendMessage(messages.getDiscordCommonInvalidArgumentCount(0)).queue();
            return;
        }

        try {
            accountManager.unregisterPlayer(e.getAuthor().getId());
            e.getChannel().sendMessage(messages.getDiscordUnregistrationSuccess(e.getAuthor().getId())).queue();
        } catch (NotFoundException ex) {
            e.getChannel().sendMessage(messages.getDiscordUnregistrationNotFound(e.getAuthor().getId())).queue();
        } catch (UnregisterUserException ex) {
            e.getChannel().sendMessage(messages.getDiscordUnregistrationFailure()).queue();
        }
    }

}
