package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RcptToHandlerCommand extends BaseHandlerCommand {
    private final Pattern patternRrcptCommand;

    public RcptToHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
        this.patternRrcptCommand = Pattern.compile("^RCPT\\sTO:\\s<([a-zA-Z0-9]+@[a-zA-Z0-9]+\\.(com))>");
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();
        Matcher matcher = patternRrcptCommand.matcher(inputLine);
        if (matcher.matches()) {
            this.sessionContext.setRecipient(matcher.group(1));
            this.sessionContext.setSessionStateRcptToDone();
            answers.add("250 2.1.5 Recipient OK");
        }
        else {
            answers.add("500 5.5.1 Syntax error, command unrecognized.");
        }

        return answers;
    }
}
