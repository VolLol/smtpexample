package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EhloHandlerCommand extends BaseHandlerCommand {
    private final Pattern patternEhloCommand;

    public EhloHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
        this.patternEhloCommand = Pattern.compile("^EHLO ((?!:\\/\\/)([a-zA-Z0-9-_]+\\.)*[a-zA-Z0-9][a-zA-Z0-9-_]+\\.[a-zA-Z]{2,11}?)");
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        Matcher m = patternEhloCommand.matcher(inputLine);
        ArrayList<String> answers = new ArrayList<>();
        if (m.matches()) {
            answers.add(success(m.group(1)));
        }
        return answers;
    }


    private String success(String ehloString) {
        sessionContext.setEhloServer(ehloString);
        sessionContext.setSessionStateEhloDone();
        return "250 " + sessionContext.getServerName() + " Hello";
    }
}
