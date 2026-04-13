package com.santidev.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CryptoWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CryptoService cryptoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void servesFrontPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("Ingresa la seed y procesa tu texto.")))
                .andExpect(content().string(containsString("Descifrar")));
    }

    @Test
    void decryptEndpointReturnsOriginalText() throws Exception {
        String seed = "demo-seed";
        String originalText = "mensaje secreto";
        String encryptedText = cryptoService.encrypt(originalText, seed);
        String requestBody = objectMapper.writeValueAsString(Map.of(
                "text", encryptedText,
                "seed", seed
        ));

        mockMvc.perform(post("/crypto/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(originalText));
    }
}
