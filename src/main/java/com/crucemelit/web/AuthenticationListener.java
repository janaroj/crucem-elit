package com.crucemelit.web;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.crucemelit.model.User;
import com.crucemelit.service.UserService;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Resource
    private UserService userService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {

        if (appEvent instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
            userService.resetLoginFailures((User) event.getAuthentication().getPrincipal());
        }
        else if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
            userService.addLoginFailure((String) event.getAuthentication().getPrincipal());
        }

    }
}