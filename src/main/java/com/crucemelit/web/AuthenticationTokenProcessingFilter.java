package com.crucemelit.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.crucemelit.util.TokenUtils;

@Component("authenticationTokenProcessingFilter")
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting a HTTP request");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader("X-Auth-Token");

        String userName = tokenUtils.getUserNameFromToken(authToken);
        if (userName != null) {
            UserDetails userDetails = userService.loadUserByUsername(userName);
            if (tokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
