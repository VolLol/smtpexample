package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;

public interface IHandlerCommand {
    public ArrayList<String> execute(String inputLine);
}
