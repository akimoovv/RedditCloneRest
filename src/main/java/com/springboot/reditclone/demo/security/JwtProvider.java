package com.springboot.reditclone.demo.security;


import com.springboot.reditclone.demo.model.User;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtProvider {

    public String generateToken(Authentication authentication) {
      User userPrincipal =  (User)authentication.getPrincipal();

      return Jwts.builder()
              .setSubject(userPrincipal.getUsername())
              .signWith(getPrivateKey())
              .compact();
    }





}
