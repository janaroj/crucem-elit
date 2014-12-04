package com.crucemelit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.util.Utility;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private static final String EMAIL = "test@email.ee";
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";

    private User user;

    @Mock
    private Gym gym;

    private List<User> mockUsers;

    @Before
    public void setup() {
        user = new User();
        user.setEmail(EMAIL);
        when(gym.getUsers()).thenReturn(mockUsers);
    }

    @Test
    public void getContactsFromGymWithNoGymTest() {
        assertEquals(Utility.EMPTY_LIST, user.getContactsFromGym());
    }

    @Test
    public void getContactsFromGymWithGymTest() {
        user.setGym(gym);
        assertEquals(mockUsers, user.getContactsFromGym());
    }

    @Test
    public void resetInvalidLoginCountTest() {
        user.resetInvalidLoginCount();
        assertEquals(0, user.getInvalidLoginCount());
    }

    @Test
    public void increaseInvalidLoginCountTest() {
        user.increaseInvalidLoginCount();
        user.increaseInvalidLoginCount();
        assertEquals(2, user.getInvalidLoginCount());
    }

    @Test
    public void getNameWithNoFirstAndLastNameTest() {
        assertEquals(EMAIL, user.getName());
    }

    @Test
    public void getNameWithOnlyFirstNameTest() {
        user.setFirstName(FIRST_NAME);
        assertEquals(FIRST_NAME, user.getName());
    }

    @Test
    public void getNameWithOnlyLastNameTest() {
        user.setLastName(LAST_NAME);
        assertEquals(LAST_NAME, user.getName());
    }

    @Test
    public void getNameWithFirstAndLastNameTest() {
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        assertEquals(FIRST_NAME + " " + LAST_NAME, user.getName());
    }

    @Test
    public void removeWorkoutTest() {
        user.addWorkout(createWorkout(1));
        user.addWorkout(createWorkout(2));
        user.addWorkout(createWorkout(3));
        assertTrue(user.getWorkouts().size() == 3);
        user.removeWorkout(3);
        user.removeWorkout(1);
        assertTrue(user.getWorkouts().size() == 1);
        assertEquals(user.getWorkouts().get(0).getId(), 2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNotExistingWorkoutTest() {
        user.removeWorkout(1);
    }

    private Workout createWorkout(long id) {
        Workout workout = new Workout();
        workout.setId(id);
        return workout;
    }

    @Test
    public void removeCommentTest() {
        user.addComment(createComment(1));
        user.addComment(createComment(2));
        user.addComment(createComment(3));
        assertTrue(user.getComments().size() == 3);
        user.removeComment(1);
        user.removeComment(3);
        assertTrue(user.getComments().size() == 1);
        assertEquals(user.getComments().get(0).getId(), 2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNotExistingCommentTest() {
        user.removeComment(1);
    }

    private Comment createComment(long id) {
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }

}
