package com.example.be12th.global.config;

import com.example.be12th.domain.user.service.CustomOidcUserService;
import com.example.be12th.domain.user.service.OAuth2LoginSuccessHandler;
import com.example.be12th.global.jwt.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomOidcUserService customOidcUserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //user
                        .requestMatchers(HttpMethod.POST, "/user/email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verify/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers("/", "/login/**", "/oauth2/**", "/user/login/success").permitAll()

                        //ranking
                        .requestMatchers(HttpMethod.GET, "/ranking").authenticated()

                        //club
                        .requestMatchers(HttpMethod.GET, "/club/{clubId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/club/favorite").permitAll()
                        .requestMatchers(HttpMethod.GET, "/club/search").permitAll()

                        //match
                        .requestMatchers(HttpMethod.GET, "/match/{matchId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/matches").authenticated()

                        //player
                        .requestMatchers(HttpMethod.GET, "/player/{playerId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/player/interest").permitAll()
                        .requestMatchers(HttpMethod.GET, "/player/search").permitAll()

                        //favorite
                        .requestMatchers(HttpMethod.POST, "/favorite/player/{playerId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/favorite/player/{playerId}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/favorite/club/{clubId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/favorite/club/{clubId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/favorite/schedule/{clubId}").authenticated()

                        //event
                        .requestMatchers(HttpMethod.GET, "/goal/{playerId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/event/{matchId}").authenticated()

                        .anyRequest().permitAll()
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                )

                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                );

        return http.build();
    }
}