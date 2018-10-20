package org.springframework.boot.peach.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class MessageSender implements Runnable {

    Logger logger = LoggerFactory.getLogger(MessageSender.class);

    static int[] serverPorts = {80,6666,6667};

    private String[] message;

    public MessageSender(String[] message) {
        this.message = message;
    }

    @Override
    public void run() {
        sendMessage();
    }

    public void sendMessage() {
        try {
            int serverPort = serverPorts[new Random().nextInt(3)];
//            Socket socket = new Socket(InetAddress.getByName("192.168.27.129"), serverPort);
            Socket socket = new Socket(InetAddress.getLocalHost(), serverPort);
            socket.setSoTimeout(10000);
            socket.setSendBufferSize(1024);
            Thread.currentThread().sleep(new Random().nextInt(100));
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            long messageLength = 0L;
            for (String messageLine : message) {
                messageLength += messageLine.length();
                socketWriter.println(messageLine);
            }
            socketWriter.flush();
            socket.shutdownOutput();

            logger.info("Port {} Send {} chars.\n",serverPort,messageLength);

            String responseMessage = responseReader.readLine();

            if ("OK".equals(responseMessage)) {
                logger.info("Message saved by server.\n");
            }
            socket.shutdownInput();

            responseReader.close();
            socketWriter.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
