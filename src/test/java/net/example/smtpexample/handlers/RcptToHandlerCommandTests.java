package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RcptToHandlerCommandTests {

    @Test
    public void correctRecipientNo1() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();

        RcptToHandlerCommand rcptToHandlerCommand = new RcptToHandlerCommand(sessionContext);
        List<String> answers = rcptToHandlerCommand.execute("RCPT TO: <kate@fabrikam.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 2.1.5 Recipient OK", answers.get(0));
        Assert.assertEquals("kate@fabrikam.com", sessionContext.getRecipient());
        Assert.assertEquals(SessionState.RCPTTO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void correctRecipientNo2() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();

        RcptToHandlerCommand rcptToHandlerCommand = new RcptToHandlerCommand(sessionContext);
        List<String> answers = rcptToHandlerCommand.execute("RCPT TO: <sergei@gmail.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 2.1.5 Recipient OK", answers.get(0));
        Assert.assertEquals("sergei@gmail.com", sessionContext.getRecipient());
        Assert.assertEquals(SessionState.RCPTTO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void incorrectCommand() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();

        RcptToHandlerCommand rcptToHandlerCommand = new RcptToHandlerCommand(sessionContext);
        List<String> answers = rcptToHandlerCommand.execute("MAIL FROM: <sergei@gmail.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("500 5.5.1 Syntax error, command unrecognized.", answers.get(0));
        Assert.assertNull(sessionContext.getRecipient());
        Assert.assertEquals(SessionState.MAILFROM_DONE, sessionContext.getSessionState());
    }
}
