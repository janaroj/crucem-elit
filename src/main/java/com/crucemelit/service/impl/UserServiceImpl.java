package com.crucemelit.service.impl;

import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.domain.Gender;
import com.crucemelit.domain.Role;
import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.dto.UserDto;
import com.crucemelit.exception.CredentialsExpiredException;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.exception.UserAlreadyExistsException;
import com.crucemelit.model.Comment;
import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.UserService;
import com.crucemelit.transformer.UserTransformer;
import com.crucemelit.util.TokenUtils;
import com.crucemelit.util.Utility;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserTransformer userTransformer;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
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
    public List<UserDto> getContactsDto() {
        User user = getCurrentUser();
        List<User> contacts = Utility.getUniqueList(user.getFriends(), user.getContactsFromGym());
        contacts.remove(user);
        return userTransformer.transformToDto(contacts);
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
        byte[] pictureBytes = getUser(id).getPicture();
        if (pictureBytes == null) {
            return "";
        }
        return Utility.getImgSourceFromBytes(pictureBytes);
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
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDto> getFriendsDto() {
        return userTransformer.transformToDto(getCurrentUser().getFriends());
    }

    @Override
    public void removeFriend(long id) {
        User user = getCurrentUser();
        User friend = getUser(id);
        user.removeFriend(friend);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void addFriend(long id) {
        User user = getCurrentUser();
        User friend = getUser(id);
        user.addFriend(friend);
        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDto authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) loadUserByUsername(username);
        user.setToken(tokenUtils.createToken(user));
        return userTransformer.transformToDtoWithAuthInfo(user);
    }

    @Override
    public UserDto getCurrentUserWithAuthInfo() {
        return userTransformer.transformToDtoWithAuthInfo(getCurrentUser());
    }

    @Override
    public UserDto getCurrentUserDto() {
        return userTransformer.transformToDto(getCurrentUser());
    }

    @Override
    public UserDto getUserDto(long id) {
        return userTransformer.transformToDto(getUser(id));
    }

    User getUser(long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    @Override
    public void createUserWorkout(Workout workout) {
        User user = getCurrentUser();
        if (user.getGym() != null) {
            workout.setGymName(user.getGym().getName());
        }
        else {
            workout.setGymName("gymless");
        }
        user.addWorkout(workout);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void fillUserWorkout(Workout workout) {
        User user = getCurrentUser();
        List<Workout> userWorkouts = user.getWorkouts();
        Workout targetWorkout = null;
        for (Workout w : userWorkouts) {
            if (w.getId() == workout.getId()) {
                targetWorkout = w;
                break;
            }
        }
        if (targetWorkout != null) {
            targetWorkout.setExerciseGroups(workout.getExerciseGroups());
        }
        userRepository.saveAndFlush(user);

    }

    @Override
    public void deleteUserWorkoutById(long id) {
        User user = getCurrentUser();
        user.removeWorkout(id);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUserRecordById(long id) {
        User user = getCurrentUser();
        user.removeRecord(id);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDto> getUsersWithAuthInfo() {
        return userTransformer.transformToDtoWithAuthInfo(userRepository.findAll());
    }

    @Override
    public void changeUserRole(long id) {
        User user = getUser(id);
        user.changeRole();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.delete(id);
    }

    @Override
    public void createUserComment(Comment comment) {
        comment.setDate(new Date());
        User user = getCurrentUser();
        user.addComment(comment);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUserCommentById(long id) {
        User user = getCurrentUser();
        user.removeComment(id);
        userRepository.saveAndFlush(user);
    }
}
