package kirpideleri.discordlogin.exceptions;

public class RegistrationKeyNotFoundException extends Exception {
    public RegistrationKeyNotFoundException() {}

    public RegistrationKeyNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public RegistrationKeyNotFoundException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
}
