package org.springframework.boot.peach.message.util;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {
    private enum EncryptMode {
        Encrypt,
        Decrypt
    }

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    private static final int DEFAULT_ENCRYPT_SIZE = 256;

    private static final String DEFAULT_ENCRYPT_ALGORITHM = "AES";

    private static byte[] doAlgorithm(byte[] sourceBytes, String key, EncryptMode encryptMethod) throws Exception {
        if (sourceBytes == null || sourceBytes.length == 0) return null;

        if (StringUtils.isEmpty(key))
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");

        int cipherMethod = Cipher.ENCRYPT_MODE;
        if (encryptMethod == EncryptMode.Decrypt)
            cipherMethod = Cipher.DECRYPT_MODE;

        KeyGenerator keyGenerator = KeyGenerator.getInstance(DEFAULT_ENCRYPT_ALGORITHM);
        keyGenerator.init(DEFAULT_ENCRYPT_SIZE, new SecureRandom(key.getBytes(CHARSET_UTF8)));
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), DEFAULT_ENCRYPT_ALGORITHM);
        Cipher aes = Cipher.getInstance(DEFAULT_ENCRYPT_ALGORITHM);
        aes.init(cipherMethod, secretKeySpec);
        byte[] encryptedBytes = aes.doFinal(sourceBytes);

        return encryptedBytes;
    }

    public static String encrypt(String source, String key) throws Exception {
        if (StringUtils.isEmpty(source)) return null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");
        }

        byte[] sourceBytes = source.getBytes(CHARSET_UTF8);
        byte[] encryptedBytes = doAlgorithm(sourceBytes, key, EncryptMode.Encrypt);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encrypted, String key) throws Exception {
        if (StringUtils.isEmpty(encrypted)) return null;
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("KEY CAN NOT BE NULL");
        }

        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        byte[] decryptedBytes = doAlgorithm(encryptedBytes, key, EncryptMode.Decrypt);

        String decrypted = new String(decryptedBytes, CHARSET_UTF8);
        return decrypted;
    }
}
