package net.example.smtpexample.exceptions;

public class CloseSessionException extends RuntimeException {

    public CloseSessionException(String message) {
        super(message);
    }
}
