package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.RegisterRequest;
import com.springboot.reditclone.demo.model.NotificationEmail;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.model.VerificationToken;
import com.springboot.reditclone.demo.repository.UserRepository;
import com.springboot.reditclone.demo.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;



    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
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

}
