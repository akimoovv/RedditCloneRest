package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.LoginRequest;
import com.springboot.reditclone.demo.dto.RegisterRequest;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.model.NotificationEmail;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.model.VerificationToken;
import com.springboot.reditclone.demo.repository.UserRepository;
import com.springboot.reditclone.demo.repository.VerificationTokenRepository;
import com.springboot.reditclone.demo.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;



    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void verifyAccount(String token) {
       VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new SpringRedditException("Invalid Token")
        );

        fetchUserEnable(verificationToken);


    }

    @Transactional
    public void fetchUserEnable(VerificationToken verificationToken) {
       String username = verificationToken.getUser().getUsername();

       User user = userRepository.findByUsername(username).orElseThrow(
               () -> new SpringRedditException("Can't find user by id")
       );

       user.setEnabled(true);
       userRepository.save(user);
    }


    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);


        userRepository.save(user);



        String token = generateVerificationToken(user);


        mailService.sendMail(new NotificationEmail(
                "Let's activate your account",
                user.getEmail(),
                "Let's click on the link to confirm yout account " +
                        "http://localhost:8080/api/auth/accountVerification/" +
                        token));


    }

    private String  generateVerificationToken(User user) {

       String token =  UUID.randomUUID().toString();

       VerificationToken verificationToken = new VerificationToken();

       verificationToken.setToken(token);
       verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);


        return token;
    }




    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(request.getUsername(), "ADMIN");
            Map<Object, Object> response = new HashMap<>();
            response.put("username", request.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

}
