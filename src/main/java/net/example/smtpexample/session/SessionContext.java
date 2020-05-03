package net.example.smtpexample.session;

import net.example.smtpexample.exceptions.IncorrectCommandException;

public class SessionContext {

    private String serverName = "smtp.example.com";
    private String ehloServer;
    private String mailFrom;
    private String recipient;
    private StringBuffer mailData;
    private SessionState sessionState;

    public SessionContext() {
        this.ehloServer = null;
        this.mailFrom = null;
        this.recipient = null;
        this.mailData = new StringBuffer();
        this.sessionState = SessionState.INITIAL_DONE;
    }

    public String showResult() {
        StringBuffer mail = new StringBuffer();
        mail.append("------------------------------------------------\r\n");
        mail.append("EHLO      = " + ehloServer + "\r\n");
        mail.append("MAIL FROM = " + mailFrom + "\r\n");
        mail.append("RCPT TO   = " + recipient + "\r\n");
        mail.append("----------------------DATA----------------------\r\n");
        mail.append(mailData);
        mail.append("------------------------------------------------\r\n");
        return mail.toString();
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public String getServerName() {
        return serverName;
    }

    public String getMailData() {
        return mailData.toString();
    }

    public String getEhloServer() {
        return ehloServer;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setSessionStateEhloDone() {
        if (sessionState.equals(SessionState.INITIAL_DONE)) {
            sessionState = SessionState.EHLO_DONE;
        } else {
            throw new IncorrectCommandException("Incorrect command, can't change session state to EHLO_DONE");
        }
    }

    public void setSessionStateMailFromDone() {
        if(sessionState.equals(SessionState.EHLO_DONE)) {
            sessionState = SessionState.MAILFROM_DONE;
        } else {
            throw new IncorrectCommandException("Incorrect command, can't change session state to MAILFROM_DONE");
        }
    }

    public void setSessionStateRcptToDone() {
        if(sessionState.equals(SessionState.MAILFROM_DONE)) {
            sessionState = SessionState.RCPTTO_DONE;
        } else {
            throw new IncorrectCommandException("Incorrect command, can't change session state to RCPTTO_DONE");
        }
    }

    public void setSessionStateDataInProcess() {
        if(sessionState.equals(SessionState.RCPTTO_DONE)) {
            sessionState = SessionState.DATA_IN_PROCESS;
        } else {
            throw new IncorrectCommandException("Incorrect command, can't change session state to DATA_IN_PROCESS");
        }
    }

    public void setSessionStateDataDone() {
        if(sessionState.equals(SessionState.DATA_IN_PROCESS)) {
            sessionState = SessionState.DATA_DONE;
        } else {
            throw new RuntimeException("Incorrect command, can't change session state to DATA_DONE");
        }
    }

    public void setEhloServer(String ehloServer) {
        this.ehloServer = ehloServer;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void appendMailData(String dataLine) {
        this.mailData.append(dataLine);
    }

}
