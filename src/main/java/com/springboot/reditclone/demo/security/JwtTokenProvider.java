package com.springboot.reditclone.demo.security;


import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;


@Service
public class JwtTokenProvider {


    private Key secretKey;


    @Value("${jwt.expiration}")
    private Long expirationTimeInMillSec;


    @PostConstruct
    public void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }


    public String generateToken(Authentication authentication) {
       User user = (User) authentication.getPrincipal();
       Date now = new Date(System.currentTimeMillis());
       Date validate = new Date(now.getTime() + expirationTimeInMillSec * 60 * 60 * 10);

       return Jwts.builder().setSubject(user.getUsername())
               .setIssuedAt(now)
                .setExpiration(validate)
               .signWith(secretKey)
               .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new SpringRedditException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }


    public String getUsernameFromJwt(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

    }


}
