package com.example.be12th.global.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

@Component
public class CookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String OAUTH2_AUTH_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = findCookie(request, OAUTH2_AUTH_REQUEST_COOKIE_NAME);

        if (cookie == null || cookie.getValue() == null || cookie.getValue().isBlank()) {
            return null;
        }

        byte[] decodedBytes = Base64.getUrlDecoder().decode(cookie.getValue());
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(decodedBytes);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        if (authorizationRequest == null) {
            deleteCookie(request, response, OAUTH2_AUTH_REQUEST_COOKIE_NAME);
            return;
        }

        byte[] serializedBytes = SerializationUtils.serialize(authorizationRequest);
        if (serializedBytes == null) {
            deleteCookie(request, response, OAUTH2_AUTH_REQUEST_COOKIE_NAME);
            return;
        }

        String cookieValue = Base64.getUrlEncoder().encodeToString(serializedBytes);
        addCookie(response, OAUTH2_AUTH_REQUEST_COOKIE_NAME, cookieValue, COOKIE_EXPIRE_SECONDS, request.isSecure());
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        OAuth2AuthorizationRequest authorizationRequest = loadAuthorizationRequest(request);
        deleteCookie(request, response, OAUTH2_AUTH_REQUEST_COOKIE_NAME);
        return authorizationRequest;
    }

    private Cookie findCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    private void addCookie(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge,
                           boolean secure) {
        String cookie = name + "=" + value
                + "; Path=/"
                + "; Max-Age=" + maxAge
                + "; HttpOnly"
                + "; SameSite=Lax"
                + (secure ? "; Secure" : "");
        response.addHeader("Set-Cookie", cookie);
    }

    private void deleteCookie(HttpServletRequest request,
                              HttpServletResponse response,
                              String name) {
        Cookie cookie = findCookie(request, name);
        if (cookie == null) {
            return;
        }

        String deleteCookie = name + "=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax"
                + (request.isSecure() ? "; Secure" : "");
        response.addHeader("Set-Cookie", deleteCookie);
    }
}
