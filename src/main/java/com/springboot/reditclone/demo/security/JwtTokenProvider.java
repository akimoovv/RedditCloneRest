package com.springboot.reditclone.demo.security;


import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.model.Role;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtTokenProvider {

    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;


    @Value("${jwt.expiration}")
    private long validityInMilliSeconds;

    @Value("${jwt.header}")
    private String authHeader;


    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);

        claims.put("role", Role.ADMIN);

        Date now = new Date();

        Date validity = new Date(now.getTime() + validityInMilliSeconds * 1000);



        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public boolean validateToken(String token) {

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw  new SpringRedditException("JWT token is expired or invalid");
        }


    }





    public Authentication getAuthentication(String token) {

        try {
            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(getUsername(token));

            return new UsernamePasswordAuthenticationToken(userDetails, "",
                    userDetails.getAuthorities());

        } catch (Exception e) {

            throw new SpringRedditException("User's been not found");
        }


    }







    public String getUsername(String token) {


        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();

    }




    public String resolveToken(HttpServletRequest request) {

        return request.getHeader(authHeader);

    }




}
