package com.example.be12th.global.config;

import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolapiConfig {

    @Bean
    public DefaultMessageService defaultMessageService(
            @Value("${SOLAPI_API_KEY}") String apiKey,
            @Value("${SOLAPI_API_SECRET}") String apiSecret
    ){
        return new DefaultMessageService(apiKey, apiSecret, "https://api.solapi.com");
    }
}
