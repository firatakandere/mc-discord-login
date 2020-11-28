package github.fakandere.discordlogin.exceptions;

public class RegisterUserException extends Exception {
    public RegisterUserException() {}
    public RegisterUserException(String errorMessage) {
        super(errorMessage);
    }
    public RegisterUserException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
}
