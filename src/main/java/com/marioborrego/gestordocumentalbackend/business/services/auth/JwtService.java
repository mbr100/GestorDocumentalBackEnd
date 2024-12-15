package com.marioborrego.gestordocumentalbackend.business.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60000));

        return Jwts.builder()
                .header()
                .type("JWT").and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateJwt(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey generateJwt() {
        return Jwts.SIG.HS256.key().build();
    }

    public Claims extractClaims(String jwt) {
        return Jwts.parser().verifyWith(generateJwt()).build().parseSignedClaims(jwt).getPayload();
    }

    public String extractUsername(String jwt) {
        return extractClaims(jwt).getSubject();
    }
}
