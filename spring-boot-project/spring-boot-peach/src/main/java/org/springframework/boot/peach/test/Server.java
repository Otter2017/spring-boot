package org.springframework.boot.peach.test;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int defaultServerPort = 6000;

    @Autowired
    private static SocketServiceConfig config;

    private static ServerSocket serverSocket = null;

    private static int serverPort = 6000;

//    static {
//        try {
//            serverPort = config.getServerPort();
//            serverSocket = new ServerSocket(serverPort);
//        } catch (Exception ex) {
//            System.err.println("NEW SOCKET ERROR  >>> " + serverPort);
//            ex.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(defaultServerPort);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (serverSocket == null) return;

        ExecutorService service= Executors.newFixedThreadPool(5);

        String savePath = Server.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println("Current Path : "+savePath);
        System.out.println("Server running ...");
        while (true) {
            try {
                Socket acceptSocket = serverSocket.accept();
//                System.out.println("Server accept");
                SocketMessageProcessor messageProcessor = new SocketMessageProcessor(acceptSocket);
                service.submit(messageProcessor);
            } catch (IOException ioException) {
                System.err.println("SOCKET ACCEPT ERROT  >>> ");
                ioException.printStackTrace();
            }
        }
    }
}
