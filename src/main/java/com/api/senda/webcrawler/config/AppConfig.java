package com.api.senda.webcrawler.config;

import com.api.senda.webcrawler.service.IHttpClient;
import com.api.senda.webcrawler.service.JsoupHttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IHttpClient httpClient() {
        return new JsoupHttpClient();
    }
}