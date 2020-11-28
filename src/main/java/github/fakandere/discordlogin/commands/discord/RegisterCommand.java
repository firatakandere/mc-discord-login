package github.fakandere.discordlogin.commands.discord;

import com.google.inject.Inject;
import github.fakandere.discordlogin.exceptions.NotFoundException;
import github.fakandere.discordlogin.exceptions.RegisterUserException;
import github.fakandere.discordlogin.utils.IAccountManager;
import github.fakandere.discordlogin.utils.IMessages;
import github.fakandere.discordlogin.utils.IDiscordCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RegisterCommand implements IDiscordCommand {

    @Inject
    public RegisterCommand(final IAccountManager accountManager, final IMessages messages) {
        this.accountManager = accountManager;
        this.messages = messages;
    }

    private final IAccountManager accountManager;
    private final IMessages messages;

    @Override
    public void execute(final GuildMessageReceivedEvent e, final String[] args) {
        if (args.length != 1) {
            e.getChannel().sendMessage(messages.getDiscordCommonInvalidArgumentCount(1)).queue();
            return;
        }
        try {
            accountManager.registerPlayer(args[0], e.getAuthor().getId());
            e.getChannel().sendMessage(messages.getDiscordRegistrationSuccess(e.getAuthor().getId())).queue();
        } catch (RegisterUserException registerUserException) {
            e.getChannel().sendMessage(messages.getDiscordRegistrationFailure()).queue();
        } catch (NotFoundException registrationKeyNotFoundException) {
            e.getChannel().sendMessage(messages.getDiscordRegistrationInvalidKey(args[0])).queue();
        }
    }
}
