package net.example.smtpexample;

import net.example.smtpexample.handlers.*;
import net.example.smtpexample.session.SessionContext;
import net.example.smtpexample.session.SessionState;

import java.io.*;
import java.util.ArrayList;

public class SmtpProcessor {
    private RcptToHandlerCommand rcptToHandler;
    private QuitHandlerCommand quitHandler;
    private SessionContext sessionContext;
    private EhloHandlerCommand ehloHandler;
    private MailFromHandlerCommand mailFromHandler;
    private DataHandlerCommand dataHandler;

    SmtpProcessor() {
        this.sessionContext = new SessionContext();
        this.quitHandler = new QuitHandlerCommand(sessionContext);
        this.ehloHandler = new EhloHandlerCommand(sessionContext);
        this.mailFromHandler = new MailFromHandlerCommand(sessionContext);
        this.rcptToHandler = new RcptToHandlerCommand(sessionContext);
        this.dataHandler = new DataHandlerCommand(sessionContext);
    }

    void execute(BufferedReader tcpIn, PrintWriter tcpOut) {
        String inputLine;
        ArrayList<String> answers = new ArrayList<>();

        tcpOut.println("Hello everybody");
        tcpOut.flush();

        try {
            while ((inputLine = tcpIn.readLine()) != null) {
                quitHandler.execute(inputLine);

                if (sessionContext.getSessionState() == SessionState.INITIAL_DONE) {
                    answers.addAll(ehloHandler.execute(inputLine));
                } else if (sessionContext.getSessionState() == SessionState.EHLO_DONE) {
                    answers.addAll(mailFromHandler.execute(inputLine));
                } else if (sessionContext.getSessionState() == SessionState.MAILFROM_DONE) {
                    answers.addAll(rcptToHandler.execute(inputLine));
                } else if (sessionContext.getSessionState() == SessionState.RCPTTO_DONE ||
                        sessionContext.getSessionState() == SessionState.DATA_IN_PROCESS) {
                    answers.addAll(dataHandler.execute(inputLine));
                }

                if (answers.size() != 0) {
                    for (String answer : answers) {
                        tcpOut.println(answer);
                    }
                    tcpOut.flush();
                    answers.clear();
                }

                if (sessionContext.getSessionState() == SessionState.DATA_DONE) {
                    tcpOut.print(sessionContext.showResult());
                    tcpOut.flush();
                }
            }
        } catch (IOException e) {
            //problem read data from tcp
            System.out.println("Error read data from socket");
            System.out.println(e.getMessage());
        }

    }
}
