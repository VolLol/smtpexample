package net.example.smtpexample.handlers;

import net.example.smtpexample.exceptions.CloseSessionException;
import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;

public class QuitHandlerCommand extends BaseHandlerCommand {
    public QuitHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        if (inputLine.equals("QUIT")) {
            throw new CloseSessionException("Close connection, client is QUIT");
        }

        return new ArrayList<>();
    }
}
