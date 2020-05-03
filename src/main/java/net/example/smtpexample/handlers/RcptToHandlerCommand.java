package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;

public class RcptToHandlerCommand extends BaseHandlerCommand {

    public RcptToHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();

        if (inputLine.equals("RCPT TO: <kate@fabrikam.com>")) {
            this.sessionContext.setRecipient(inputLine);
            this.sessionContext.setSessionStateRcptToDone();
            answers.add("250 2.1.5 Recipient OK");
        }

        return answers;
    }
}
