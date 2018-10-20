package org.springframework.boot.peach.message;

import org.springframework.boot.peach.message.enums.*;
import org.springframework.boot.peach.message.exception.MessageParseException;
import org.springframework.boot.peach.message.util.ByteUtil;
import org.springframework.boot.peach.message.util.EnumUtil;

import java.util.Arrays;

public class MessageHeader {
    private static final int HEADER_LENGTH = 17 + 53;

    private MessageVersion messageVersion;
    private MessageType messageType;
    private MessagePriority priority;
    private HashType hashType;
    private MessageCharset charset;
    private EncryptMethod encryptMethod;
    private int contentLength;
    private long createTimeMillis;
    // Fix length = 32 (Last 32 chars)
    private String contentHash;
    // Fix length = 16 (AZaz09)
    private String deviceUID;
    // Fix length = 4
    private String deviceToken;

    public MessageVersion getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(MessageVersion messageVersion) {
        this.messageVersion = messageVersion;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessagePriority getPriority() {
        return priority;
    }

    public void setPriority(MessagePriority priority) {
        this.priority = priority;
    }

    public HashType getHashType() {
        return hashType;
    }

    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public void setCreateTimeMillis(long createTimeMillis) {
        this.createTimeMillis = createTimeMillis;
    }

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public String getDeviceUID() {
        return deviceUID;
    }

    public void setDeviceUID(String deviceUID) {
        this.deviceUID = deviceUID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public MessageCharset getCharset() {
        return charset;
    }

    public void setCharset(MessageCharset charset) {
        this.charset = charset;
    }

    public EncryptMethod getEncryptMethod() {
        return encryptMethod;
    }

    public void setEncryptMethod(EncryptMethod encryptMethod) {
        this.encryptMethod = encryptMethod;
    }

    /**
     * 返回消息头的固定长度
     */
    public static int validLength() {
        return HEADER_LENGTH;
    }

    public static MessageHeader parse(byte[] headerBytes) throws MessageParseException {
        MessageHeader messageHeader;
        if (headerBytes.length != MessageHeader.validLength())
            throw new MessageParseException("Invalid message header length.");

        try {
            messageHeader = new MessageHeader();
            messageHeader.setMessageVersion(EnumUtil.parse(MessageVersion.class, headerBytes[0]));
            messageHeader.setMessageType(EnumUtil.parse(MessageType.class, headerBytes[1]));
            messageHeader.setPriority(EnumUtil.parse(MessagePriority.class, headerBytes[2]));
            messageHeader.setHashType(EnumUtil.parse(HashType.class, headerBytes[3]));
            messageHeader.setCharset(EnumUtil.parse(MessageCharset.class, headerBytes[4]));
            messageHeader.setEncryptMethod(EnumUtil.parse(EncryptMethod.class, headerBytes[5]));
            /**
             * Arrays.copyOfRange(bytes,n,m)
             * m < bytes.length 拷贝[n,m)
             * m = bytes.length 拷贝[n,m]
             * m > bytes.length 拷贝[n,m]，并在后面加0
             * 返回的数组元素个数都为
             */
            int contentLength = ByteUtil.getInteger(Arrays.copyOfRange(headerBytes, 6, 10));
            messageHeader.setContentLength(contentLength);
            long createTime = ByteUtil.getLong(Arrays.copyOfRange(headerBytes, 10, 18));
            messageHeader.setCreateTimeMillis(createTime);

            String contentHash = new String(Arrays.copyOfRange(headerBytes, 18, 50), MessageCharset.ASCII.getCharset());
            messageHeader.setContentHash(contentHash);
            String deviceUID = new String(Arrays.copyOfRange(headerBytes, 50, 66), MessageCharset.ASCII.getCharset());
            messageHeader.setDeviceUID(deviceUID);
            String deviceToken = new String(Arrays.copyOfRange(headerBytes, 66, 70), MessageCharset.UTF8.getCharset());
            messageHeader.setDeviceToken(deviceToken);
        } catch (Exception ex) {
            throw new MessageParseException("Invalid message header parameter :" + ex.getMessage());
        }

        return messageHeader;
    }

    public static byte[] toBytes(MessageHeader messageHeader) {
        if (messageHeader == null) return null;
        byte[] messageHeaderBytes = new byte[70];
        messageHeaderBytes[0] = EnumUtil.toByte(messageHeader.getMessageVersion());
        messageHeaderBytes[1] = EnumUtil.toByte(messageHeader.getMessageType());
        messageHeaderBytes[2] = EnumUtil.toByte(messageHeader.getPriority());
        messageHeaderBytes[3] = EnumUtil.toByte(messageHeader.getHashType());
        messageHeaderBytes[4] = EnumUtil.toByte(messageHeader.getCharset());
        messageHeaderBytes[5] = EnumUtil.toByte(messageHeader.getEncryptMethod());
        byte[] contentLengthBytes = ByteUtil.toBytes(messageHeader.getContentLength());
        System.arraycopy(contentLengthBytes, 0, messageHeaderBytes, 6, 4);
        byte[] createTimeBytes = ByteUtil.toBytes(messageHeader.getCreateTimeMillis());
        System.arraycopy(createTimeBytes, 0, messageHeaderBytes, 10, 8);
        byte[] hashBytes = messageHeader.getContentHash().getBytes(MessageCharset.ASCII.getCharset());
        System.arraycopy(hashBytes, 0, messageHeaderBytes, 18, hashBytes.length);
        byte[] deviceUIDbytes = messageHeader.getDeviceUID().getBytes(MessageCharset.ASCII.getCharset());
        System.arraycopy(deviceUIDbytes, 0, messageHeaderBytes, 50, deviceUIDbytes.length);
        byte[] deviceTokenbytes = messageHeader.getDeviceToken().getBytes(MessageCharset.UTF8.getCharset());
        System.arraycopy(deviceTokenbytes, 0, messageHeaderBytes, 66, deviceTokenbytes.length);

        return messageHeaderBytes;
    }
}
