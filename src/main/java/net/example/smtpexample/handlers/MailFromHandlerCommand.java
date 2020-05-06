package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailFromHandlerCommand extends BaseHandlerCommand {
    private final Pattern patternMailCompile;

    public MailFromHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
        this.patternMailCompile = Pattern.compile("^MAIL\\sFROM:\\s<([a-zA-Z0-9]+@(\\w+)\\.\\w+)>");
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();
        Matcher matcher = patternMailCompile.matcher(inputLine);
        if (matcher.matches()) {
            if (matcher.group(2).equals("contoso")) {
                this.sessionContext.setMailFrom(matcher.group(1));
                this.sessionContext.setSessionStateMailFromDone();
                answers.add("250 2.1.0 Sender OK");
            } else {
                answers.add("553 5.1.8 Sender e-mail address domain does not exist.");
            }
        } else {
            answers.add("500 5.5.1 Syntax error, command unrecognized.");
        }
        return answers;
    }
}
