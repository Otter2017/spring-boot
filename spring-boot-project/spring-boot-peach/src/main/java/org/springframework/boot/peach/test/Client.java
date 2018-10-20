package org.springframework.boot.peach.test;

import org.springframework.boot.peach.DateTimeFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()~`;',.:?ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String[] COMMANDS = {"REG", "UPD", "DEL", "MOD", "ADD", "MVR"};

    static final int MESSAGE_LENGTH = 10240;
    static final int SEND_MESSAGE_COUNT = 20;

    private static Queue<String[]> messageQueue = new LinkedList<>();

    private static String[] generateMessage() {
        StringBuffer messageBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < MESSAGE_LENGTH; i++) {
            int charIndex = random.nextInt(VALID_CHARS.length());
            messageBuffer.append(VALID_CHARS.substring(charIndex, charIndex + 1));
        }
        String messageContent = messageBuffer.toString();
        String command = COMMANDS[random.nextInt(COMMANDS.length)];
        String sendTime = DateTimeFormat.format(new Date());

        String[] message = {command, sendTime, messageContent};
        return message;
    }

    public static void generateMessageQueue() {
        for (int i = 0; i < SEND_MESSAGE_COUNT; i++) {
            messageQueue.add(generateMessage());
        }
    }

    public static void main(String[] args) {
        ExecutorService sendService = Executors.newFixedThreadPool(10);
        generateMessageQueue();
        try {
            while (messageQueue.size() > 0) {
                String[] message = messageQueue.remove();
                MessageSender sender = new MessageSender(message);
                sendService.submit(sender);
            }
            sendService.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
