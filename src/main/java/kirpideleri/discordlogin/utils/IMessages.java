package kirpideleri.discordlogin.utils;

public interface IMessages {
    String getDiscordRegistrationSuccess(final String discordUserID);
    String getDiscordRegistrationFailure();
    String getDiscordRegistrationInvalidKey(final String registrationKey);
    String getDiscordCommonInvalidArgumentCount(final int expectedArgumentCount);
    String getDiscordLoginMessage(final String serverName, final String joinedIpAddress);
    String getDiscordNoOnlineUsers();
}
