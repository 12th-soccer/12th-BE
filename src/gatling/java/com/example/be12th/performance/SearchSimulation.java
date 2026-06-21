package com.example.be12th.performance;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SearchSimulation extends Simulation {

    private static final String BASE_URL = env("BASE_URL", "http://localhost:8080");
    private static final int SEASON = intEnv("SEASON", 2025);
    private static final double PLAYER_RPS = doubleEnv("PLAYER_RPS", 0.5);
    private static final double TEAM_RPS = doubleEnv("TEAM_RPS", 0.5);
    private static final int RAMP_SECONDS = intEnv("RAMP_SECONDS", 10);
    private static final int DURATION_SECONDS = intEnv("DURATION_SECONDS", 20);
    private static final double MAX_FAILED_PERCENT = doubleEnv("MAX_FAILED_PERCENT", 1.0);
    private static final int MAX_P95_MILLIS = intEnv("MAX_P95_MILLIS", 2_000);

    private static final FeederBuilder<Object> PLAYER_KEYWORDS = keywordFeeder(
            "playerKeyword",
            env("PLAYER_KEYWORDS", "Gustav,Seung,Jin-su")
    );
    private static final FeederBuilder<Object> TEAM_KEYWORDS = keywordFeeder(
            "teamKeyword",
            env("TEAM_KEYWORDS", "서울,울산,전북")
    );

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/SearchSimulation");

    private final ScenarioBuilder playerSearch = scenario("Player search")
            .exec(feed(PLAYER_KEYWORDS))
            .exec(
                    http("GET /search/player")
                            .get("/search/player")
                            .queryParam("keyword", "#{playerKeyword}")
                            .queryParam("season", SEASON)
                            .queryParam("page", 1)
                            .check(status().is(200))
            );

    private final ScenarioBuilder teamSearch = scenario("Team search")
            .exec(feed(TEAM_KEYWORDS))
            .exec(
                    http("GET /search/team")
                            .get("/search/team")
                            .queryParam("keyword", "#{teamKeyword}")
                            .queryParam("season", SEASON)
                            .check(status().is(200))
            );

    public SearchSimulation() {
        requirePositive("PLAYER_RPS", PLAYER_RPS);
        requirePositive("TEAM_RPS", TEAM_RPS);
        requirePositive("RAMP_SECONDS", RAMP_SECONDS);
        requirePositive("DURATION_SECONDS", DURATION_SECONDS);
        requirePositive("MAX_P95_MILLIS", MAX_P95_MILLIS);

        setUp(
                playerSearch.injectOpen(
                        rampUsersPerSec(0.1).to(PLAYER_RPS).during(Duration.ofSeconds(RAMP_SECONDS)),
                        constantUsersPerSec(PLAYER_RPS).during(Duration.ofSeconds(DURATION_SECONDS))
                ),
                teamSearch.injectOpen(
                        rampUsersPerSec(0.1).to(TEAM_RPS).during(Duration.ofSeconds(RAMP_SECONDS)),
                        constantUsersPerSec(TEAM_RPS).during(Duration.ofSeconds(DURATION_SECONDS))
                )
        )
                .protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().lte(MAX_FAILED_PERCENT),
                        global().responseTime().percentile3().lte(MAX_P95_MILLIS)
                );
    }

    private static FeederBuilder<Object> keywordFeeder(String key, String rawKeywords) {
        List<Map<String, Object>> records = Arrays.stream(rawKeywords.split(","))
                .map(String::trim)
                .filter(keyword -> !keyword.isEmpty())
                .map(keyword -> Map.<String, Object>of(key, keyword))
                .toList();

        if (records.isEmpty()) {
            throw new IllegalArgumentException(key + " must contain at least one keyword");
        }

        return io.gatling.javaapi.core.CoreDsl.listFeeder(records).random();
    }

    private static String env(String name, String defaultValue) {
        return System.getenv().getOrDefault(name, defaultValue);
    }

    private static int intEnv(String name, int defaultValue) {
        return Integer.parseInt(env(name, Integer.toString(defaultValue)));
    }

    private static double doubleEnv(String name, double defaultValue) {
        return Double.parseDouble(env(name, Double.toString(defaultValue)));
    }

    private static void requirePositive(String name, double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than 0");
        }
    }
}
