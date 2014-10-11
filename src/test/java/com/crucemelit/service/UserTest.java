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

    private User user;

    @Mock
    private Gym gym;

    private List<User> mockUsers;

    @Before
    public void setup() {
        user = new User();
        when(gym.getUsers()).thenReturn(mockUsers);
    }

    @Test
    public void getContactsFromGymTestWithNoGym() {
        assertEquals(Utility.EMPTY_LIST, user.getContactsFromGym());
    }

    @Test
    public void getContactsFromGymTestWithGym() {
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
}
