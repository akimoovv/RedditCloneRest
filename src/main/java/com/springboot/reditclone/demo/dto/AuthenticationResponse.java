package com.springboot.reditclone.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthenticationResponse {

    private String username;
    private String authenticationToken;

    public AuthenticationResponse(String username, String authenticationToken) {
        this.username = username;
        this.authenticationToken = authenticationToken;
    }
}
