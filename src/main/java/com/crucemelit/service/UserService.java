package com.crucemelit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.crucemelit.dto.UserDto;
import com.crucemelit.dto.WorkoutDto;
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

    void joinGym(Gym gym);

    void sendInviteEmail(String email);

    void forgotPassword(String email);

    List<WorkoutDto> getUserWorkoutsDto();

    void updateUser(User user);

    List<UserDto> getFriendsDto();

    void removeFriend(long id);

    void addFriend(long id);

    UserDto authenticate(String username, String password);

    UserDto getCurrentUserWithAuthInfo();

    void createWorkout(Workout workout);

    List<UserDto> getUsersWithAuthInfo();

    void changeUserRole(long id);

    void deleteUser(long id);

}
