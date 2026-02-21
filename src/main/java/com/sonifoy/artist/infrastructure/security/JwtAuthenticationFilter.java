package com.sonifoy.artist.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        try {
            String userEmail = jwtService.extractUsername(token);
            if (userEmail != null && jwtService.isTokenValid(token, userEmail)) {
                // In a real microservice, we might fetch roles from the token claims or a
                // user-service call
                // For now, valid token = authenticated. Roles can be added to token claims.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        Collections.emptyList()); // Roles should come from token claims

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
        } catch (Exception e) {
            // Log error
        }

        return chain.filter(exchange);
    }
}
