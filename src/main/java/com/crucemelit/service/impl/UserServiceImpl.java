package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.UserService;
import com.crucemelit.util.Utility;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void addLoginFailure(String email) {
        User user = (User) loadUserByUsername(email);
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
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getContacts() {
        User user = getUser(getCurrentUser().getId());
        List<User> contacts = Utility.getUniqueList(user.getFriends(), user.getContactsFromGym());
        contacts.remove(user);
        return contacts;
    }

    @Override
    public void leaveGym() {
        User user = getUser(getCurrentUser().getId());
        user.setGym(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (User) auth.getPrincipal();
        }
        else {
            return null;
        }
    }

}
