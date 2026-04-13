package com.santidev.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody CryptoRequest request) {
        try {
            String encrypted = cryptoService.encrypt(request.getText(), request.getSeed());
            return ResponseEntity.ok(encrypted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error encrypting: " + e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody CryptoRequest request) {
        try {
            String decrypted = cryptoService.decrypt(request.getText(), request.getSeed());
            return ResponseEntity.ok(decrypted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error decrypting: " + e.getMessage());
        }
    }

    public static class CryptoRequest {
        private String text;
        private String seed;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }
    }
}