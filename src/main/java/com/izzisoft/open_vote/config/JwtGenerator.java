package com.izzisoft.open_vote.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    private final RsaKeyConfig rsaKeyConfig;

    public JwtGenerator(RsaKeyConfig rsaKeyConfig) {
        this.rsaKeyConfig = rsaKeyConfig;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(rsaKeyConfig.privateKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(rsaKeyConfig.publicKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(rsaKeyConfig.publicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
