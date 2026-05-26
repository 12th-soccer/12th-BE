package com.example.be12th.global.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.time.Duration;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String REDIS_KEY_PREFIX = "oauth2:authorization:";
    private static final Duration AUTHORIZATION_REQUEST_TTL = Duration.ofMinutes(3);

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        String state = getState(request);
        if (state == null) {
            log.warn("OAuth authorization request load failed: state is missing");
            return null;
        }

        OAuth2AuthorizationRequest authorizationRequest = getAuthorizationRequest(state);
        log.info("OAuth authorization request load from Redis: state={}, found={}", state, authorizationRequest != null);
        return authorizationRequest;
    }

    @Override
    public void saveAuthorizationRequest(
            OAuth2AuthorizationRequest authorizationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }

        String state = authorizationRequest.getState();
        redisTemplate.opsForValue().set(
                redisKey(state),
                serialize(authorizationRequest),
                AUTHORIZATION_REQUEST_TTL
        );
        log.info("OAuth authorization request saved to Redis: state={}", state);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        String state = getState(request);
        if (state == null) {
            log.warn("OAuth authorization request remove failed: state is missing");
            return null;
        }

        OAuth2AuthorizationRequest authorizationRequest = getAuthorizationRequest(state);
        redisTemplate.delete(redisKey(state));
        log.info("OAuth authorization request removed from Redis: state={}, found={}", state, authorizationRequest != null);
        return authorizationRequest;
    }

    private OAuth2AuthorizationRequest getAuthorizationRequest(String state) {
        Object value = redisTemplate.opsForValue().get(redisKey(state));
        if (!(value instanceof String serializedValue)) {
            return null;
        }

        return deserialize(serializedValue);
    }

    private String getState(HttpServletRequest request) {
        String state = request.getParameter("state");
        if (state == null || state.isBlank()) {
            return null;
        }

        return state;
    }

    private String redisKey(String state) {
        return REDIS_KEY_PREFIX + state;
    }

    private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(authorizationRequest));
    }

    private OAuth2AuthorizationRequest deserialize(String value) {
        byte[] bytes = Base64.getUrlDecoder().decode(value);
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(bytes);
    }
}
