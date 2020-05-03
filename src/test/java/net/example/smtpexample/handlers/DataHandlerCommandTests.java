package net.example.smtpexample.handlers;

import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DataHandlerCommandTests {

    @Test
    public void checkStartCollectData() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();
        sessionContext.setSessionStateRcptToDone();

        DataHandlerCommand dataHandlerCommand = new DataHandlerCommand(sessionContext);
        List<String> answers = dataHandlerCommand.execute("DATA");

        Assert.assertEquals("354 Start mail input; end with <CRLF>.<CRLF>", answers.get(0));
        Assert.assertEquals("", sessionContext.getMailData());
        Assert.assertEquals(SessionState.DATA_IN_PROCESS, sessionContext.getSessionState());
    }


    @Test
    public void incorrectCommandNo1() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();
        sessionContext.setSessionStateRcptToDone();

        DataHandlerCommand dataHandlerCommand = new DataHandlerCommand(sessionContext);
        List<String> answers = dataHandlerCommand.execute("EHLO ooooo2.asd.example.com");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("500 5.5.1 Syntax error, command unrecognized.", answers.get(0));
        Assert.assertEquals("", sessionContext.getMailData());
        Assert.assertEquals(SessionState.RCPTTO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void incorrectCommandNo2() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();
        sessionContext.setSessionStateRcptToDone();

        DataHandlerCommand dataHandlerCommand = new DataHandlerCommand(sessionContext);
        List<String> answers = dataHandlerCommand.execute("asdasdasdadlkmlmasd");

        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("500 5.5.1 Syntax error, command unrecognized.", answers.get(0));
        Assert.assertEquals("", sessionContext.getMailData());
        Assert.assertEquals(SessionState.RCPTTO_DONE, sessionContext.getSessionState());
    }

    @Test
    public void checkProcessInputData() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();
        sessionContext.setSessionStateRcptToDone();

        DataHandlerCommand dataHandlerCommand = new DataHandlerCommand(sessionContext);
        List<String> answers = dataHandlerCommand.execute("DATA");
        answers.clear();

        answers.addAll(dataHandlerCommand.execute("Input text"));
        Assert.assertEquals(0, answers.size());

        Assert.assertEquals("Input text\n", sessionContext.getMailData());
        Assert.assertEquals(SessionState.DATA_IN_PROCESS, sessionContext.getSessionState());
    }

    @Test
    public void endDataProcess() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSessionStateEhloDone();
        sessionContext.setSessionStateMailFromDone();
        sessionContext.setSessionStateRcptToDone();

        DataHandlerCommand dataHandlerCommand = new DataHandlerCommand(sessionContext);
        List<String> answers = dataHandlerCommand.execute("DATA");
        answers.clear();

        answers.addAll(dataHandlerCommand.execute("Input text"));
        Assert.assertEquals(0, answers.size());

        Assert.assertEquals("Input text\n", sessionContext.getMailData());
        Assert.assertEquals(SessionState.DATA_IN_PROCESS, sessionContext.getSessionState());

        answers.addAll(dataHandlerCommand.execute("."));
        Assert.assertEquals("Input text\n.\n", sessionContext.getMailData());
        Assert.assertEquals(SessionState.DATA_DONE, sessionContext.getSessionState());
    }

}
