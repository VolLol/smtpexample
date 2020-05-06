package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;

import java.util.ArrayList;

public class DataHandlerCommand extends BaseHandlerCommand {

    public DataHandlerCommand(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public ArrayList<String> execute(String inputLine) {
        ArrayList<String> answers = new ArrayList<>();
        if (inputLine.equals("DATA") && sessionContext.getSessionState() == SessionState.RCPTTO_DONE) {
            sessionContext.setSessionStateDataInProcess();
            answers.add("354 Start mail input; end with <CRLF>.<CRLF>");
        } else if (sessionContext.getSessionState() == SessionState.DATA_IN_PROCESS) {
            sessionContext.appendMailData(inputLine);
            sessionContext.appendMailData("\n");
        }
        else {
            answers.add("500 5.5.1 Syntax error, command unrecognized.");
        }
        if (isDataEnd()) {
            answers.add("250 2.6.0 Queued mail for delivery");
            sessionContext.setSessionStateDataDone();
        }

        return answers;
    }

    private boolean isDataEnd() {
        if (sessionContext.getMailData().length() > 2) {
            String last3Symbols = sessionContext.getMailData().substring(sessionContext.getMailData().length() - 3);
            return last3Symbols.equals("\n.\n");
        }
        return false;
    }
}
