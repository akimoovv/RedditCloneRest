package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.LoginRequest;
import com.springboot.reditclone.demo.dto.RegisterRequest;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.model.NotificationEmail;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.model.VerificationToken;
import com.springboot.reditclone.demo.repository.UserRepository;
import com.springboot.reditclone.demo.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;



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




    public void login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
    }

}
