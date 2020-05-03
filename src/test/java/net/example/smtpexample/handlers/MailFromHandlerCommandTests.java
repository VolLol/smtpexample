package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MailFromHandlerCommandTests {

    @Test
    public void correctSenderNo1() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();

        MailFromHandlerCommand mailFromHandlerCommand = new MailFromHandlerCommand(sessionContext);
        List<String> answers = mailFromHandlerCommand.execute("MAIL FROM: <chris@contoso.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 2.1.0 Sender OK", answers.get(0));
        Assert.assertEquals("chris@contoso.com", sessionContext.getMailFrom());
        Assert.assertEquals(SessionState.MAILFROM_DONE, sessionContext.getSessionState());
    }

    @Test
    public void correctSenderNo2() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();

        MailFromHandlerCommand mailFromHandlerCommand = new MailFromHandlerCommand(sessionContext);
        List<String> answers = mailFromHandlerCommand.execute("MAIL FROM: <alex@contoso.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 2.1.0 Sender OK", answers.get(0));
        Assert.assertEquals("alex@contoso.com", sessionContext.getMailFrom());
        Assert.assertEquals(SessionState.MAILFROM_DONE, sessionContext.getSessionState());
    }

    @Test
    public void incorrectSenderNo1() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();

        MailFromHandlerCommand mailFromHandlerCommand = new MailFromHandlerCommand(sessionContext);
        List<String> answers = mailFromHandlerCommand.execute("MAIL FROM: <sergei@gmail.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("553 5.1.8 Sender e-mail address domain does not exist.", answers.get(0));
        Assert.assertNull(sessionContext.getMailFrom());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void incorrectCommandNo1() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();

        MailFromHandlerCommand mailFromHandlerCommand = new MailFromHandlerCommand(sessionContext);
        List<String> answers = mailFromHandlerCommand.execute("DDDD");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("500 5.5.1 Syntax error, command unrecognized.", answers.get(0));
        Assert.assertNull(sessionContext.getMailFrom());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void incorrectCommandNo2() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();

        MailFromHandlerCommand mailFromHandlerCommand = new MailFromHandlerCommand(sessionContext);
        List<String> answers = mailFromHandlerCommand.execute("RCPT TO: <kate@fabrikam.com>");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("500 5.5.1 Syntax error, command unrecognized.", answers.get(0));
        Assert.assertNull(sessionContext.getMailFrom());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }
}
