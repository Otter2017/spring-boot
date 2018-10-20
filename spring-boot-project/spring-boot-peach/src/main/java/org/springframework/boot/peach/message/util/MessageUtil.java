package org.springframework.boot.peach.message.util;

import org.springframework.boot.peach.message.enums.EncryptMethod;

public class MessageUtil {

    public static byte[] decryptBytes(byte[] encryptedBytes, EncryptMethod encryptMethod, String encryptKey) {
        //TODO decrypt
        return encryptedBytes;
    }

    public static byte[] encryptBytes(byte[] decryptedBytes, EncryptMethod encryptMethod, String decryptKey) {
        //TODO encrypt
        return decryptedBytes;
    }
}
