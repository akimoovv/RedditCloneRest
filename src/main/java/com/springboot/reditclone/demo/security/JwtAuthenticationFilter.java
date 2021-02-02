package com.springboot.reditclone.demo.security;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(httpServletRequest);

         if(token != null && jwtTokenProvider.validateToken(token)) {

             String username = jwtTokenProvider.getUsernameFromJwt(token);


             UserDetails  userDetails = userDetailsService.loadUserByUsername(username);

             UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                     null, userDetails.getAuthorities());

             authentication.setDetails(new WebAuthenticationDetailsSource()
                     .buildDetails(httpServletRequest));

             SecurityContextHolder.getContext().setAuthentication(authentication);

         }

        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("Header");
    }
}
