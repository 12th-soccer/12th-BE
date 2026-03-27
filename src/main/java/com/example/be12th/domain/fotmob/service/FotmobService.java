package com.example.be12th.domain.fotmob.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FotmobService {
    private WebClient webClient;

    public FotmobService(){
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))
                .build();

        this.webClient = WebClient.builder()
                .baseUrl("https://www.fotmob.com")
                .exchangeStrategies(strategies)
                .build();
    }

    public String getLeagueData(){
        return webClient.get()
                .uri("https://www.fotmob.com/api/data/tltable?leagueId=9080")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Accept", "application/json")
                .header("Referer", "https://www.fotmob.com/")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTeamDetails(Long fotmobId) {
        return webClient.get()
                .uri("https://www.fotmob.com/api/data/teams?id=" + fotmobId + "&ccode3=KOR")
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .header("Accept", "application/json, text/plain, */*")
                .header("Referer", "https://www.fotmob.com/")
                .header("Origin", "https://www.fotmob.com")
                .header("Connection", "keep-alive")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}