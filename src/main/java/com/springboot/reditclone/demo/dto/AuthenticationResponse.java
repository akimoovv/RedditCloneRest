package com.springboot.reditclone.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String username;
    private String authenticationToken;

    public AuthenticationResponse() {
    }

}
