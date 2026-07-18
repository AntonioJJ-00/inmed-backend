package com.inmed.security;

import com.inmed.security.blacklist.JwtBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtBlacklistService jwtBlacklistService;
    private final JwtProperties jwtProperties;
    public String generateToken(
            String username,
            String role
    ){
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim(
                        "role",
                        role
                )
                .claim(
                        "type",
                        "ACCESS"
                )
                .issuer(
                        jwtProperties.getIssuer()
                )
                .audience()
                .add(
                        jwtProperties.getAudience()
                )
                .and()
                .id(
                        UUID.randomUUID()
                                .toString()
                )
                .issuedAt(now)
                .expiration(
                        new Date(
                                now.getTime()
                                        +
                                        jwtProperties.getExpiration()
                        )
                )
                .signWith(
                        getSigningKey()
                )
                .compact();
    }

    public Claims extractAllClaims(
            String token
    ){
        return Jwts.parser()
                .verifyWith(
                        getSigningKey()
                )
                .requireIssuer(
                        jwtProperties.getIssuer()
                )
                .requireAudience(
                        jwtProperties.getAudience()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(
            String token
    ){
        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token
    ){
        try{
            if(jwtBlacklistService.isBlacklisted(token)){
                return false;
            }
            Claims claims =
                    extractAllClaims(token);
            return claims.getExpiration()
                    .after(
                            new Date()
                    );
        }catch(Exception e){
            return false;
        }
    }

    public long getExpiration(
            String token
    ){
        return extractAllClaims(token)
                .getExpiration()
                .getTime()
                -
                System.currentTimeMillis();
    }

    public String extractJti(
            String token
    ){
        return extractAllClaims(token)
                .getId();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                jwtProperties
                        .getSecret()
                        .getBytes(
                                StandardCharsets.UTF_8
                        )
        );
    }
}