package br.com.instagram.security.jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtFilter implements WebFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenService tokenService;

    public JwtFilter(JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwt = getTokenFromHeader(exchange.getRequest());
        if (StringUtils.hasText(jwt) && this.tokenService.validateToken(jwt)) {
            Authentication authentication = this.tokenService.getAuthentication(jwt);
            return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return chain.filter(exchange);
    }

    private String getTokenFromHeader(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") && !bearerToken.isEmpty()) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
