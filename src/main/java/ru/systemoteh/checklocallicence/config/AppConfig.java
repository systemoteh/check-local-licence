package ru.systemoteh.checklocallicence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.encryption")
    public EncryptionProperties encryptionProperties() {
        return new EncryptionProperties();
    }

    @Bean
    public SecretKey secretKey(EncryptionProperties properties) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(properties.getKey());
        return new SecretKeySpec(decodedKey, "AES");
    }

    @Getter
    @Setter
    public static class EncryptionProperties {
        private String key;
        private String encryptedDate;
    }
}