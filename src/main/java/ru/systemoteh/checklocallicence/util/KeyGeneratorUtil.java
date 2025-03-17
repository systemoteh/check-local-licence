package ru.systemoteh.checklocallicence.util;

import ru.systemoteh.checklocallicence.crypto.DateCrypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        // Генерация ключа
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey key = keyGen.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        // Шифрование даты
        LocalDate date = LocalDate.now().plusDays(7); // Пример: дата через неделю
        String encryptedDate = DateCrypto.encryptDate(date, key);

        System.out.println("Ключ: " + base64Key);
        System.out.println("Зашифрованная дата: " + encryptedDate);
    }
}
