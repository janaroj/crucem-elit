package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.UserService;

/**
 * UserService that accesses the spring credentials.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new User("");
        }

        return new User(((UserDetails) authentication.getPrincipal()).getUsername());
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
