package ru.nsu.brusn.lab1.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.RestTemplate;
import ru.nsu.brusn.lab1.model.task.CrackHashTaskManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvc {

    private List<HttpMessageConverter<?>> getXmlMessageConverters() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(marshaller);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(marshallingConverter);
        return converters;
    }
    @Bean
    public RestTemplate getRestTemplate() {
        var restTemplate = new RestTemplate();
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
