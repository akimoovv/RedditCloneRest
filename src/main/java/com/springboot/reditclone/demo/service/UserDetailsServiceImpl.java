package com.springboot.reditclone.demo.service;

import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("User " + username  + " hasn't been found", HttpStatus.UNAUTHORIZED));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
             true,
                user.isEnabled(),
                true,
                true,
                new HashSet<>());
    }
}
