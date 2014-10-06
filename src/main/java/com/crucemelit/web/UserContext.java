package com.crucemelit.web;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import com.crucemelit.model.User;

@Component
public class UserContext {

    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SessionAuthenticationException("Unable to fetch authentication data from user session");
        }
        return authentication;
    }

    public User getUser() {
        Authentication auth = getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (User) auth.getPrincipal();
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public TimeZone getTimeZone() {
        return LocaleContextHolder.getTimeZone();
    }

}
