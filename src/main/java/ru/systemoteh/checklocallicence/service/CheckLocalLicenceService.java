package ru.systemoteh.checklocallicence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.systemoteh.checklocallicence.config.AppConfig;
import ru.systemoteh.checklocallicence.crypto.DateCrypto;

import javax.crypto.SecretKey;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckLocalLicenceService {

    private final SecretKey secretKey;
    private final AppConfig.EncryptionProperties properties;
    private final ApplicationContext context;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        checkLocalLicence();
    }

    @Scheduled(cron = "${midnight.cron}")
    private void checkLocalLicenceSchedule() {
        checkLocalLicence();
    }

    private void checkLocalLicence() {
        log.info("Start loading CheckLocalLicenceService");
        if (isLocalLicenceExpired()) {
            stop();
        }
    }

    private boolean isLocalLicenceExpired() {
        try {
            LocalDate storedDate = DateCrypto.decryptDate(properties.getEncryptedDate(), secretKey);
            LocalDate currentDate = LocalDate.now();
            return currentDate.isAfter(storedDate);
        } catch (Exception e) {
            log.error("Couldn't get date", e);
            return true;
        }
    }

    public void stop() {
        log.error("Your license to use the program has expired. Please contact the support service");

        // todo отправить e-mail или отправить http-запрос на наш сервер

        // Закрытие контекста и завершение приложения
        ((ConfigurableApplicationContext) context).close();
        System.exit(0); // Опционально, для гарантированного завершения JVM
    }
}
