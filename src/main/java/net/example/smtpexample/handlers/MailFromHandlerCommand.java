package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailFromHandlerCommand extends BaseHandlerCommand {
    private final Pattern patternMailCompile;

    public MailFromHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
        this.patternMailCompile = Pattern.compile("^MAIL FROM: (<+(([a-zAz0-_]{1,16})+@((contoso).com))+>)");
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();
        Matcher matcher = patternMailCompile.matcher(inputLine);
        if (matcher.matches()) {
            inputLine = matcher.group(2);
            this.sessionContext.setMailFrom(inputLine);
            this.sessionContext.setSessionStateMailFromDone();
            answers.add("250 2.1.0 Sender OK");
        } else if (inputLine.contains("gmail")) {
            answers.add("553 5.1.8 Sender e-mail address domain does not exist.");
        } else {
            answers.add("500 5.5.1 Syntax error, command unrecognized.");
        }
        return answers;
    }
}
