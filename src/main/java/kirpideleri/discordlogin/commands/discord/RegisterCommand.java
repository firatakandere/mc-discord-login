package kirpideleri.discordlogin.commands.discord;

import com.google.inject.Inject;
import kirpideleri.discordlogin.exceptions.RegisterUserException;
import kirpideleri.discordlogin.exceptions.RegistrationKeyNotFoundException;
import kirpideleri.discordlogin.utils.IAccountManager;
import kirpideleri.discordlogin.utils.IDiscordCommand;
import kirpideleri.discordlogin.utils.IMessages;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RegisterCommand implements IDiscordCommand {

    @Inject
    private IAccountManager accountManager;

    @Inject
    private IMessages messages;

    @Override
    public void execute(GuildMessageReceivedEvent e, String[] args) {
        if (args.length != 1) {
            e.getChannel().sendMessage(messages.getDiscordCommonInvalidArgumentCount(1)).queue();
            return;
        }
        try {
            accountManager.registerPlayer(args[0], e.getAuthor().getId());
            e.getChannel().sendMessage(messages.getDiscordRegistrationSuccess(e.getAuthor().getId())).queue();
        } catch (RegisterUserException registerUserException) {
            e.getChannel().sendMessage(messages.getDiscordRegistrationFailure());
        } catch (RegistrationKeyNotFoundException registrationKeyNotFoundException) {
            e.getChannel().sendMessage(messages.getDiscordRegistrationInvalidKey(args[0])).queue();
        }
    }
}
