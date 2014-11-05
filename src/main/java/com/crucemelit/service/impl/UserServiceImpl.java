package com.crucemelit.service.impl;

import java.util.List;

import lombok.SneakyThrows;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.domain.Gender;
import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.exception.CredentialsExpiredException;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.exception.UserAlreadyExistsException;
import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;
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
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
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
        user.increaseInvalidLoginCount();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void resetLoginFailures(User user) {
        user.resetInvalidLoginCount();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void register(User user) {
        verifyUserDoesntExist(user.getEmail());
        seDefaultValuesForUser(user);
        userRepository.saveAndFlush(user);
    }

    private void verifyUserDoesntExist(String email) {
        try {
            loadUserByUsername(email);
            throw new UserAlreadyExistsException();
        }
        catch (UsernameNotFoundException expected) {
        }
    }

    private void seDefaultValuesForUser(User user) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setRole(Role.USER);
        user.setGender(Gender.UNDEFINED);
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
            if (auth.getPrincipal() instanceof User) {
                return getUser(((User) auth.getPrincipal()).getId());
            }
        }
        throw new CredentialsExpiredException();
    }

    @Override
    public void setPicture(byte[] picture, long... id) {
        User user = getCurrentUser();
        user.setPicture(picture);
        userRepository.saveAndFlush(user);
    }

    @Override
    public String getPicture(long id) {
        return Utility.getImgSourceFromBytes(getUser(id).getPicture());
    }

    @Override
    @SneakyThrows
    public void sendInviteEmail(String email) {
        User currentUser = getCurrentUser();
        String subject = "CrossFit application!";
        String text = "Hey, check out our application at crucem-elit.herokuapp.com";

        Utility.sendInvite(currentUser.getEmail(), email, subject, text);
    }

    @Override
    public List<Suggestion> search(String term) {
        return Utility.getSuggestions(userRepository.findBySearchTerm(term), SuggestionType.USER);
    }

    @SneakyThrows
    @Override
    public void forgotPassword(String email) {
        User user = (User) loadUserByUsername(email);
        String password = Utility.generateRandomPassword(8);
        user.setPasswordHash(encoder.encode(password));
        userRepository.saveAndFlush(user);

        String subject = "Your new crossfit password";
        String text = "Here is your new crossfit password: " + password;

        Utility.sendForgottenPassword(email, subject, text);
    }

    @Override
    public List<Workout> getUserWorkouts() {
        List<Workout> workouts = getCurrentUser().getWorkouts();
        for (Workout workout : workouts) {
            Hibernate.initialize(workout.getExercises());
        }
        return workouts;
    }

    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }
}
