package com.crucemelit.service;

import java.util.List;

import com.crucemelit.model.User;

public interface UserService {

    List<User> getUsers();

    void addLoginFailure(String email);

    void resetLoginFailures(User user);

    void register(User user);

    User getUser(long id);

    List<User> getContacts();

}
