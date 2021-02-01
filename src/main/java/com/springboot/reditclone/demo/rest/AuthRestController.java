package com.springboot.reditclone.demo.rest;


import com.springboot.reditclone.demo.dto.LoginRequest;
import com.springboot.reditclone.demo.dto.RegisterRequest;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {


    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequset) {

        authService.signUp(registerRequset);

        return new ResponseEntity<>("User registration was successful. Username " +
               "\"" + registerRequset.getUsername() + "\"" + " and email " +
                "\"" +  registerRequset.getEmail() + "\"", HttpStatus.OK);
    }



    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {

        authService.verifyAccount(token);


        return new ResponseEntity<>("Account activeted succesfully", HttpStatus.OK);
    }





    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

       return  authService.login(loginRequest, "ADMIN" );

    }





    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {

        SecurityContextLogoutHandler handler =
                new SecurityContextLogoutHandler();

        handler.logout(request,response, null);


        return new ResponseEntity<>("Successful logout" , HttpStatus.OK);
    }




}
