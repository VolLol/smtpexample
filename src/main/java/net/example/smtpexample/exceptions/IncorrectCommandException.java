package net.example.smtpexample.exceptions;

public class IncorrectCommandException extends RuntimeException {

    public IncorrectCommandException(String message) {
        super(message);
    }
}
