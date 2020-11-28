package github.fakandere.discordlogin.exceptions;

public class UnregisterUserException extends Exception {
    public UnregisterUserException() {}
    public UnregisterUserException(String errorMessage) { super(errorMessage); }
    public UnregisterUserException(String errorMessage, Throwable ex) { super(errorMessage, ex); }
}
