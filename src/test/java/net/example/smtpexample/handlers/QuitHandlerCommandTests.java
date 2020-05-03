package net.example.smtpexample.handlers;

import net.example.smtpexample.exceptions.CloseSessionException;
import net.example.smtpexample.session.SessionContext;
import org.junit.Test;

public class QuitHandlerCommandTests {

    @Test(expected = CloseSessionException.class)
    public void correctCommand() {
        SessionContext sessionContext = new SessionContext();

        QuitHandlerCommand quitHandlerCommand = new QuitHandlerCommand(sessionContext);
        quitHandlerCommand.execute("QUIT");
    }

    @Test()
    public void incorrectCommand() {
        SessionContext sessionContext = new SessionContext();

        QuitHandlerCommand quitHandlerCommand = new QuitHandlerCommand(sessionContext);
        quitHandlerCommand.execute("DDD");
    }

}
