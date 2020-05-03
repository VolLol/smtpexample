package net.example.smtpexample.session;

public enum SessionState {
    INITIAL_DONE,
    EHLO_DONE,
    MAILFROM_DONE,
    RCPTTO_DONE,
    DATA_IN_PROCESS,
    DATA_DONE
}