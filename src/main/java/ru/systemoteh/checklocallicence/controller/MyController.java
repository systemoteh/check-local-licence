package ru.systemoteh.checklocallicence.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.systemoteh.checklocallicence.service.CheckLocalLicenceService;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final CheckLocalLicenceService checkLocalLicenceService;

    @SneakyThrows
    @GetMapping("/start")
    public String run(){

        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }

        return "start";
    }

    @SneakyThrows
    @GetMapping("/stop")
    public String stop(){
        checkLocalLicenceService.stop();
        return "stop";
    }
}
