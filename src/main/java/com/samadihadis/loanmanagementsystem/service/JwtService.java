package com.samadihadis.loanmanagementsystem.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
