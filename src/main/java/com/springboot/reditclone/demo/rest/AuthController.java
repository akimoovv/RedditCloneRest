package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.RegisterRequest;
import com.springboot.reditclone.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequset) {

        authService.signUp(registerRequset);

        return new ResponseEntity<>("User registration was successful. Uername" +
                registerRequset.getUsername() + " and email " +
                registerRequset.getEmail(), HttpStatus.OK);
    }


}
