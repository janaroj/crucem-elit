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

import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.UserService;
import com.crucemelit.util.Utility;
import com.crucemelit.web.Role;

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
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
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
        user.setRole(Role.USER);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getContacts() {
        User user = getCurrentUser();
        List<User> contacts = Utility.getUniqueList(user.getFriends(), user.getContactsFromGym());
        contacts.remove(user);
        return contacts;
    }

    @Override
    public void joinGym(Gym gym) {
        User user = getCurrentUser();
        user.setGym(gym);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void leaveGym() {
        User user = getCurrentUser();
        user.setGym(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return getUser(((User) auth.getPrincipal()).getId());
        }
        else {
            return null;
        }
    }

}
