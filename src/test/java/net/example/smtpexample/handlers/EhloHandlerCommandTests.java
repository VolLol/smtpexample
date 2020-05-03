package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EhloHandlerCommandTests {

    @Test
    public void checkEhloHostNo1() {
        SessionContext sessionContext = new SessionContext();
        EhloHandlerCommand ehloHandlerCommand = new EhloHandlerCommand(sessionContext);
        List<String> answers = ehloHandlerCommand.execute("EHLO s1.example.com");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 " + sessionContext.getServerName() + " Hello", answers.get(0));
        Assert.assertEquals("s1.example.com", sessionContext.getEhloServer());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void checkEhloHostNo2() {
        SessionContext sessionContext = new SessionContext();
        EhloHandlerCommand ehloHandlerCommand = new EhloHandlerCommand(sessionContext);
        List<String> answers = ehloHandlerCommand.execute("EHLO ooooo2.example.com");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 " + sessionContext.getServerName() + " Hello", answers.get(0));
        Assert.assertEquals("ooooo2.example.com", sessionContext.getEhloServer());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void checkEhloHostNo3() {
        SessionContext sessionContext = new SessionContext();
        EhloHandlerCommand ehloHandlerCommand = new EhloHandlerCommand(sessionContext);
        List<String> answers = ehloHandlerCommand.execute("EHLO ooooo2.asd.example.com");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("250 " + sessionContext.getServerName() + " Hello", answers.get(0));
        Assert.assertEquals("ooooo2.asd.example.com", sessionContext.getEhloServer());
        Assert.assertEquals(SessionState.EHLO_DONE, sessionContext.getSessionState());
    }
}
