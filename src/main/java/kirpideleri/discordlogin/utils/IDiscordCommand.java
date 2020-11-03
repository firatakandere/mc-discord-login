package kirpideleri.discordlogin.utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface IDiscordCommand {
    void execute(final GuildMessageReceivedEvent e, final String[] args);
}
