package com.springboot.reditclone.demo.service;


import com.springboot.reditclone.demo.dto.AuthenticationResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final JwtTokenProvider jwtTokenProvider;



    @Transactional
    public void verifyAccount(String token) {
       VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new SpringRedditException("Invalid Token", HttpStatus.UNAUTHORIZED)
        );

        fetchUserEnable(verificationToken);


    }

    @Transactional
    public void fetchUserEnable(VerificationToken verificationToken) {
       String username = verificationToken.getUser().getUsername();

       User user = userRepository.findByUsername(username).orElseThrow(
               () -> new SpringRedditException("Can't find user by id", HttpStatus.UNAUTHORIZED)
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




    public AuthenticationResponse login(LoginRequest request) {

       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthenticationResponse(request.getUsername() , token);


    }


    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User userDetails =
                ( org.springframework.security.core.userdetails.User)SecurityContextHolder.
                        getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new SpringRedditException("User hasn't been found"));


        return user;
    }

}
