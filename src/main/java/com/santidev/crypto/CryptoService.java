package com.santidev.crypto;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class CryptoService {

    private static final String ALGORITHM = "Blowfish";

    public String encrypt(String text, String seed) throws Exception {
        SecretKey key = generateKeyFromSeed(seed);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedText, String seed) throws Exception {
        SecretKey key = generateKeyFromSeed(seed);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted);
    }

    private SecretKey generateKeyFromSeed(String seed) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(seed.getBytes());
        // Blowfish key up to 56 bytes (448 bits), so take first 56 bytes
        byte[] blowfishKey = new byte[56];
        System.arraycopy(keyBytes, 0, blowfishKey, 0, Math.min(keyBytes.length, 56));
        return new SecretKeySpec(blowfishKey, ALGORITHM);
    }
}