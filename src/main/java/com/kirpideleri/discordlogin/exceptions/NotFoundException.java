package com.kirpideleri.discordlogin.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException() {}
    public NotFoundException(String errorMessage) { super(errorMessage); }
    public NotFoundException(String errorMessage, Throwable ex) { super(errorMessage, ex); }
}
