package kirpideleri.discordlogin.commands.discord;

import com.google.inject.Inject;
import kirpideleri.discordlogin.utils.IAccountManager;
import kirpideleri.discordlogin.utils.IDiscordCommand;
import kirpideleri.discordlogin.utils.IMessages;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WhoIsOnlineCommand implements IDiscordCommand {

    @Inject
    private IAccountManager accountManager;

    @Inject
    private IMessages messages;

    @Override
    public void execute(GuildMessageReceivedEvent e, String[] args)
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

        List<String> onlinePlayerNames = new ArrayList<String>();

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
