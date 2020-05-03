package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;

public class MailFromHandlerCommand extends BaseHandlerCommand {

    public MailFromHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();

        if (inputLine.equals("MAIL FROM: <chris@contoso.com>")) {
            this.sessionContext.setMailFrom(inputLine);
            this.sessionContext.setSessionStateMailFromDone();
            answers.add("250 2.1.0 Sender OK");
        } else {
            answers.add("500 5.5.1 Syntax error, command unrecognized.");
        }
        return answers;
    }
}
