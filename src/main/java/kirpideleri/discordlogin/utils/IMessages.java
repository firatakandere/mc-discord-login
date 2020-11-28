package kirpideleri.discordlogin.utils;

public interface IMessages {
    String getDiscordRegistrationSuccess(final String discordUserID);
    String getDiscordRegistrationFailure();
    String getDiscordRegistrationInvalidKey(final String registrationKey);
    String getDiscordUnregistrationSuccess(final String discordUserID);
    String getDiscordUnregistrationNotFound(final String discordUserID);
    String getDiscordUnregistrationFailure();
    String getDiscordCommonInvalidArgumentCount(final int expectedArgumentCount);
    String getDiscordLoginMessage(final String serverName, final String joinedIpAddress);
    String getDiscordNoOnlineUsers();

    String getServerTimeoutFailure();
}
