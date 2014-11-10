package com.crucemelit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;

public interface UserService extends UserDetailsService, SearchService, PictureService {

    List<User> getUsers();

    void addLoginFailure(String email);

    void resetLoginFailures(User user);

    void register(User user);

    User getUser(long id);

    List<User> getContacts();

    void leaveGym();

    User getCurrentUser();

    void joinGym(Gym gym);

    void sendInviteEmail(String email);

    void forgotPassword(String email);

    List<Workout> getUserWorkouts();

    void updateUser(User user);

    List<User> getFriends();

    void removeFriend(long id);

    void addFriend(long id);

}
