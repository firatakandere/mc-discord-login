package kirpideleri.discordlogin.utils;

public interface IMessages {
    String getDiscordRegistrationSuccess(String discordUserID);
    String getDiscordRegistrationFailure();
    String getDiscordRegistrationInvalidKey(String registrationKey);
    String getDiscordCommonInvalidArgumentCount(int expectedArgumentCount);
    String getDiscordLoginMessage(String serverName, String joinedIpAddress);
}
