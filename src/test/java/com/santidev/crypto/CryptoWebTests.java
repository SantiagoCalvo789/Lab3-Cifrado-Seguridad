package com.santidev.crypto;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CryptoWebTests {

    private final CryptoService cryptoService = new CryptoService();

    @Test
    void frontPageResourceExists() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/index.html")) {
            assertNotNull(inputStream, "The front page should be packaged as a static resource");
            String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            assertTrue(html.contains("Ingresa la seed y procesa tu texto."));
            assertTrue(html.contains("Descifrar"));
        }
    }

    @Test
    void encryptionRoundTripKeepsOriginalText() throws Exception {
        String seed = "demo-seed";
        String originalText = "mensaje secreto";

        String encryptedText = cryptoService.encrypt(originalText, seed);
        String decryptedText = cryptoService.decrypt(encryptedText, seed);

        assertEquals(originalText, decryptedText);
    }
}
