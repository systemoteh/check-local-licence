package ru.systemoteh.checklocallicence.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckLocalLicenceService {

    private final ApplicationContext context;
    private final ThreadPoolTaskExecutor taskExecutor; // Ваш пул потоков (если есть)

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
        // todo получить зашифрованную дату и сравнить с текущей
        return true;
    }

    public void stop() {
        log.error("Your license to use the program has expired. Please contact the support service");

        // todo отправить e-mail или отправить http-запрос на наш сервер

        // Закрытие контекста и завершение приложения
        ((ConfigurableApplicationContext) context).close();
        System.exit(0); // Опционально, для гарантированного завершения JVM
    }
}
