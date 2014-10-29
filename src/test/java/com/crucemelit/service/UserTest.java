package com.crucemelit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
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

}
