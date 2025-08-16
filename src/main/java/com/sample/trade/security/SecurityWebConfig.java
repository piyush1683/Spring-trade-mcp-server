package com.sample.trade.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityWebConfig {

    private final AuthenticationProvider authenticationProvider = null;
    private final JWTFilter jwtFilter = new JWTFilter(new JWTTokenGenService());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("login", "/login", "/index.html", "index.html", "mcp-client.html",
                                "/mcp-client.html", "/mcp/**", "/topics/**", "/app/**", "/endpoints", "/health",
                                "/ai/**", "/actuator/**",
                                "/ai/**/**")
                        .permitAll()
                        .anyRequest().authenticated()
                        // .authenticationProvider(authenticationProvider)
                        .and()
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class));
        return http.csrf().disable().build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignore static directories from Security Filter Chain
        return web -> web.ignoring().requestMatchers("/login", "login.html", "/css/**", "/js/**", "/images/**",
                "/favicon.ico", "/error", "/actuator/**");
    }

}
