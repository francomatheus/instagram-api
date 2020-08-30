package br.com.instagram.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${instagram-secretjwt.value}")
    private String secretKey;

    private Long timeExpiration = 24*60*60L*1000;


    public String generateToken(Authentication authentication){

        Date dateNow = new Date();
        Date dateExpiration = new Date(dateNow.getTime() + timeExpiration);

        return Jwts.builder()
                .setIssuer("Instagram-api")
                .setSubject(authentication.getName())
                .setIssuedAt(dateNow)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .serializeToJsonWith(new JacksonSerializer<>())
                .compact();


    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

/*        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());*/

        List<SimpleGrantedAuthority> admin = Arrays.asList("USER").stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", admin);

        return new UsernamePasswordAuthenticationToken(principal, null, admin);

    }

    public boolean validateToken(String tokenFromHeader) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenFromHeader);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

}
