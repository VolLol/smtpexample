package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;

public class BaseHandlerCommand implements IHandlerCommand {
    protected SessionContext sessionContext;

    public BaseHandlerCommand(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        return null;
    }
}
