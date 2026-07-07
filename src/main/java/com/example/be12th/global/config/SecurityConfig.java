package com.example.be12th.global.config;

import com.example.be12th.domain.user.service.CustomOidcUserService;
import com.example.be12th.domain.user.service.OAuth2LoginSuccessHandler;
import com.example.be12th.global.error.ErrorResponse;
import com.example.be12th.global.error.GlobalExceptionFilter;
import com.example.be12th.global.error.exception.ErrorCode;
import com.example.be12th.global.jwt.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private static final String CHARACTER_ENCODING = "UTF-8";

    private final JwtTokenFilter jwtTokenFilter;
    private final ObjectMapper objectMapper;
    private final CustomOidcUserService customOidcUserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        //websocket
                        .requestMatchers("/ws", "/ws/**").permitAll()

                        //chat
                        .requestMatchers(HttpMethod.GET, "/chat/*/messages").authenticated()

                        //health
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()

                        //user
                        .requestMatchers(HttpMethod.POST, "/user/email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verify/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers("/", "/login/**", "/oauth2/**", "/user/login/success").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/info").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/logout").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/me").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/user/name").authenticated()
                        .requestMatchers(HttpMethod.POST, "/user/phone/send").authenticated()
                        .requestMatchers(HttpMethod.POST, "/user/phone/verify").authenticated()

                        //ranking
                        .requestMatchers(HttpMethod.GET, "/ranking").authenticated()

                        //team
                        .requestMatchers(HttpMethod.POST, "/teams/sync").authenticated()
                        .requestMatchers(HttpMethod.GET, "/teams/kleague1").authenticated()
                        .requestMatchers(HttpMethod.GET, "/teams/kleague2").authenticated()
                        .requestMatchers(HttpMethod.GET, "/teams/*").authenticated()

                        //match
                        .requestMatchers(HttpMethod.GET, "/match/kleague1").authenticated()
                        .requestMatchers(HttpMethod.GET, "/match/kleague2").authenticated()
                        .requestMatchers(HttpMethod.GET, "/match/*").authenticated()

                        //player
                        .requestMatchers(HttpMethod.POST, "/player/sync").authenticated()
                        .requestMatchers(HttpMethod.GET, "/player/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/player/kleague1").authenticated()
                        .requestMatchers(HttpMethod.GET, "/player/kleague2").authenticated()
                        .requestMatchers(HttpMethod.GET, "/player/*").authenticated()

                        //favorite
                        .requestMatchers(HttpMethod.POST, "/favorite/player/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/favorite/player/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/favorite/team/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/favorite/team/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/favorite/team").authenticated()
                        .requestMatchers(HttpMethod.GET, "/favorite/player").authenticated()

                        //goal
                        .requestMatchers(HttpMethod.GET, "/goals/*").authenticated()

                        //search
                        .requestMatchers(HttpMethod.GET, "/search/player").permitAll()
                        .requestMatchers(HttpMethod.GET, "/search/team").permitAll()

                        //fcm
                        .requestMatchers(HttpMethod.POST, "/fcm/tokens").authenticated()
                        .requestMatchers(HttpMethod.POST, "/fcm/test").authenticated()
                        .requestMatchers(HttpMethod.GET, "/notifications/settings").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/notifications/settings").authenticated()

                        //spoiler
                        .requestMatchers(HttpMethod.GET, "/spoiler").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/spoiler").authenticated()

                        //recruitment
                        .requestMatchers(HttpMethod.POST, "/recruitment").authenticated()
                        .requestMatchers(HttpMethod.GET, "/recruitment").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recruitment/*").permitAll()

                        //join
                        .requestMatchers(HttpMethod.POST, "/join/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/join").authenticated()

                        //notice
                        .requestMatchers(HttpMethod.POST, "/notice/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/notice/*").authenticated()

                        //comment
                        .requestMatchers(HttpMethod.POST, "/comment/*").authenticated()
                        .requestMatchers(HttpMethod.GET, "/comment/*").authenticated()

                        .anyRequest().denyAll()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                writeErrorResponse(response, ErrorCode.UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                writeErrorResponse(response, ErrorCode.FORBIDDEN)
                        )
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .addFilterBefore(new GlobalExceptionFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, GlobalExceptionFilter.class)

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)
                        )
                        .failureHandler((request, response, exception) -> {
                            log.error("OAuth2 login failed", exception);
                            writeErrorResponse(response, ErrorCode.UNAUTHORIZED);
                        })
                        .successHandler(oAuth2LoginSuccessHandler)
                );

        return http.build();
    }

    private void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        if (response.isCommitted()) {
            return;
        }

        response.setStatus(errorCode.getStatusCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        objectMapper.writeValue(
                response.getWriter(),
                ErrorResponse.of(errorCode, errorCode.getErrorMessage())
        );
    }
}
