package com.example.be12th.global.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String OAUTH2_STATE_COOKIE_NAME = "oauth2_state";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    private final Map<String, OAuth2AuthorizationRequest> authorizationRequests = new ConcurrentHashMap<>();

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        String state = getState(request);
        if (state == null) {
            return null;
        }

        return authorizationRequests.get(state);
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
        authorizationRequests.put(state, authorizationRequest);
        addCookie(response, state, COOKIE_EXPIRE_SECONDS);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        String state = getState(request);
        deleteCookie(request, response);

        if (state == null) {
            return null;
        }

        return authorizationRequests.remove(state);
    }

    private String getState(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_STATE_COOKIE_NAME);
        if (cookie == null || cookie.getValue() == null || cookie.getValue().isBlank()) {
            return null;
        }

        return cookie.getValue();
    }

    private void addCookie(HttpServletResponse response, String value, int maxAge) {
        Cookie cookie = new Cookie(OAUTH2_STATE_COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_STATE_COOKIE_NAME);
        if (cookie == null) {
            return;
        }

        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
