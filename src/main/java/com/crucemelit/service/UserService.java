package com.crucemelit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.crucemelit.dto.UserDto;
import com.crucemelit.model.Comment;
import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;

public interface UserService extends UserDetailsService, SearchService, PictureService {

    void addLoginFailure(String email);

    void resetLoginFailures(User user);

    void register(User user);

    UserDto getUserDto(long id);

    List<UserDto> getContactsDto();

    void leaveGym();

    UserDto getCurrentUserDto();

    User getCurrentUser();
    
    User getCurrentUserWithGymUsers();

    void joinGym(Gym gym);

    void sendInviteEmail(String email);

    void forgotPassword(String email);

    void updateUser(User user);

    List<UserDto> getFriendsDto();

    void removeFriend(long id);

    void addFriend(long id);

    UserDto authenticate(String username, String password);

    UserDto getCurrentUserWithAuthInfo();

    void createUserWorkout(Workout workout);

    void deleteUserWorkoutById(long id);

    List<UserDto> getUsersWithAuthInfo();

    void changeUserRole(long id);

    void deleteUser(long id);

    void createUserComment(Comment comment);

    void deleteUserCommentById(long id);

}
