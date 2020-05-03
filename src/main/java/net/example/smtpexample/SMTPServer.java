package net.example.smtpexample;

import net.example.smtpexample.exceptions.CloseSessionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPServer {
    public static void main(String[] args) {
        int listenPort = 2525;
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(listenPort);
            System.out.println("SMTP Server waiting client...");
            while (true) {
                clientSocket = serverSocket.accept();
                PrintWriter tcpOut =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader tcpIn = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                try {
                    System.out.println("Client connected");
                    SmtpProcessor smtpProcessor = new SmtpProcessor();
                    smtpProcessor.execute(tcpIn, tcpOut);
                } catch (CloseSessionException e) {
                    System.out.println("Close client connection");
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("IOError ");
            System.out.println(e.getMessage());
        }
    }

}
