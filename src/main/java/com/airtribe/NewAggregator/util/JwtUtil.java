package com.airtribe.NewAggregator.util;

import com.airtribe.NewAggregator.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private String secretKey = "sampleTest";

    private long jwtExpTime = 36000000;


    public String generateJWTtoken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", user.getEmail());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public long getExpTime() {
        return jwtExpTime;
    }

}
