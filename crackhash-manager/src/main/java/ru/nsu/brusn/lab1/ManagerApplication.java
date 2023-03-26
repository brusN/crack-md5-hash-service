package ru.nsu.brusn.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;

@SpringBootApplication
public class ManagerApplication {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CrackHashTaskManager getTaskManager() {
        return new CrackHashTaskManager();
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}