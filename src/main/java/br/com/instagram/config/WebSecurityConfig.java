package br.com.instagram.config;

import br.com.instagram.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        //@formatter:off
        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/swagger-ui.html","/v3/api-docs/**","/webjars/**").permitAll()
                .pathMatchers("/v0/user/**").permitAll()
                .pathMatchers("/v0/**").authenticated()
                .and()
                .build();
        //@formatter:on
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager (UserDetailsService userDetailsService){
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }

}
