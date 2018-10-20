package org.springframework.boot.peach.message;

import org.springframework.boot.peach.message.util.MessageUtil;

import java.util.Arrays;
import java.util.HashMap;

public class Message {
    static final String SEGMENT_SEPERATOR = "§θ";

    private MessageHeader header;
    private HashMap<String, Segment> segments;

    public Message() {
        header = null;
        segments = new HashMap<>();
    }

    public MessageHeader getHeader() {
        return this.header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public HashMap<String, Segment> getSegments() {
        return segments;
    }

    public void setSegments(HashMap<String, Segment> segments) {
        this.segments = segments;
    }

    public Segment getSegment(String segmentName) {
        return segments.get(segmentName);
    }

    public void putSegment(Segment segment) {
        this.segments.put(segment.getName(), segment);
    }

    public static Message parse(byte[] messageBytes) throws Exception {
        if (messageBytes == null || messageBytes.length == 0)
            throw new NullPointerException("Message must has content.");
        if (messageBytes.length < MessageHeader.validLength())
            throw new IllegalArgumentException("Invalid messageBytes.");
        byte[] headerBytes = Arrays.copyOfRange(messageBytes, 0, MessageHeader.validLength());
        MessageHeader header = MessageHeader.parse(headerBytes);
        byte[] segmentBytes = Arrays.copyOfRange(messageBytes, MessageHeader.validLength(), MessageHeader.validLength() + header.getContentLength());
        segmentBytes = MessageUtil.decryptBytes(segmentBytes, header.getEncryptMethod(), "");
        String messageContent = new String(segmentBytes, header.getCharset().getCharset());
        String[] segmentContents = messageContent.split(SEGMENT_SEPERATOR);
        HashMap<String, Segment> segments = new HashMap<>();
        for (String segmentContent : segmentContents) {
            Segment segment = Segment.parse(segmentContent);
            segments.put(segment.getName(), segment);
        }
        Message message = new Message();
        message.setHeader(header);
        message.setSegments(segments);
        return message;
    }

    public byte[] toBytes() {
        byte[] headerBytes = MessageHeader.toBytes(this.header);
        StringBuffer messageContent = new StringBuffer();
        int i = 0;
        for (Segment segment : segments.values()) {
            if (i > 0)
                messageContent.append(SEGMENT_SEPERATOR);
            messageContent.append(segment.toString());
            i++;
        }
        byte[] segmentBytes = messageContent.toString().getBytes(this.header.getCharset().getCharset());
        segmentBytes = MessageUtil.encryptBytes(segmentBytes, header.getEncryptMethod(), "");
        byte[] messageBytes = new byte[headerBytes.length + segmentBytes.length];
        System.arraycopy(headerBytes, 0, messageBytes, 0, headerBytes.length);
        System.arraycopy(segmentBytes, 0, messageBytes, headerBytes.length, segmentBytes.length);
        return messageBytes;
    }
}
