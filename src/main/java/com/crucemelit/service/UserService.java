package com.crucemelit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.crucemelit.model.Gym;
import com.crucemelit.model.User;

public interface UserService extends UserDetailsService {

    List<User> getUsers();

    void addLoginFailure(String email);

    void resetLoginFailures(User user);

    void register(User user);

    User getUser(long id);

    List<User> getContacts();

    void leaveGym();

    User getCurrentUser();

    void joinGym(Gym gym);

    void setProfilePicture(byte[] picture);

    String getProfilePicture(long id);

    void sendInviteEmail(String email);

    List<User> search(String term);

}
