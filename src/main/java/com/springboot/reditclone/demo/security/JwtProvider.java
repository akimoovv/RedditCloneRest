package com.springboot.reditclone.demo.security;


import com.springboot.reditclone.demo.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

    public String generateToken(Authentication authentication) {
        User user = (User)(authentication.getPrincipal());

        return null;
    }
}
