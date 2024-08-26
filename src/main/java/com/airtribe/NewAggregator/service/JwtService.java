package com.airtribe.NewAggregator.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final String secretKey = "0E654ACB6F4BB78AE105A53BDE251383B7D614C0CD324F83AA8BAD0DF73FD0A4";
    private final long expTime = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        String token =  Jwts.builder()
                            .claim("username", userDetails.getUsername())
                            .subject(userDetails.getUsername())
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .expiration(new Date(System.currentTimeMillis() + expTime))
                            .signWith(generateKey())
                            .compact();

        return token;
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims;
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }
}
