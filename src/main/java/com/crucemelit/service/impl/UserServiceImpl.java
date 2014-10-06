package com.crucemelit.service.impl;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.GymService;
import com.crucemelit.service.UserService;
import com.crucemelit.web.Role;

/**
 * UserService that accesses the spring credentials.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GymService gymService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email);
        user.setAuthorities(AuthorityUtils.createAuthorityList(Role.USER.name()));
        return user;
    }

    @Override
    public void addLoginFailure(String email) {
        User user = loadUserByUsername(email);
        user.setInvalidLoginCount(user.getInvalidLoginCount() + 1);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void resetLoginFailures(User user) {
        user.setInvalidLoginCount(0);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void register(User user) {
        user.setPasswordHash(encoder.encode(new String(Base64.decodeBase64(user.getPasswordHash())))); // UGLY
        userRepository.save(user);
    }
}
