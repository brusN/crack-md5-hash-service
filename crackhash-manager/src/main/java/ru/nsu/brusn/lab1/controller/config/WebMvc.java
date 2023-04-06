package ru.nsu.brusn.lab1.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;

import java.util.ArrayList;


@Configuration
public class WebMvc {
    @Bean
    public RestTemplate getRestTemplate() {
        var clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(2000);
        var restTemplate = new RestTemplate(clientHttpRequestFactory);
        var messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    @Bean
    public CrackHashTaskManager getTaskManager() {
        return new CrackHashTaskManager();
    }
}
