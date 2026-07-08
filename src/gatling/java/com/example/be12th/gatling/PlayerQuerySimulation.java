package com.example.be12th.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PlayerQuerySimulation extends Simulation {

    private static final String BASE_URL = System.getProperty("baseUrl", "http://localhost:8080");
    private static final String SEASON = System.getProperty("season", "2024");
    private static final String PAGE = System.getProperty("page", "1");
    private static final String PLAYER_ID = System.getProperty("playerId", "1");
    private static final String TOKEN = System.getProperty("token", "");
    private static final int REPEAT_COUNT = Integer.parseInt(System.getProperty("repeatCount", "100"));

    private final Map<String, String> authHeaders = TOKEN.isBlank()
            ? Map.of()
            : Map.of("Authorization", "Bearer " + TOKEN);

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private final ScenarioBuilder playerQueryScenario = scenario("Player Query")
            .repeat(REPEAT_COUNT).on(
                    exec(http("KLeague1 player list")
                            .get("/player/kleague1")
                            .queryParam("season", SEASON)
                            .queryParam("page", PAGE)
                            .headers(authHeaders)
                            .check(status().is(200)))
                            .exec(http("KLeague2 player list")
                                    .get("/player/kleague2")
                                    .queryParam("season", SEASON)
                                    .queryParam("page", PAGE)
                                    .headers(authHeaders)
                                    .check(status().is(200)))
                            .exec(http("Player detail")
                                    .get("/player/" + PLAYER_ID)
                                    .queryParam("season", SEASON)
                                    .headers(authHeaders)
                                    .check(status().is(200)))
            );

    {
        setUp(
                playerQueryScenario.injectOpen(atOnceUsers(1))
        )
                .protocols(httpProtocol)
                .assertions(
                        global().successfulRequests().percent().gte(95.0),
                        global().responseTime().percentile3().lt(1000)
                );
    }
}
