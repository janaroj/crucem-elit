package com.crucemelit.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    private List<User> mockUsers;

    @Before
    public void setUp() {
        when(userRepository.findAll()).thenReturn(mockUsers);
    }

    @Test
    public void getUsersTest() {
        assertEquals(mockUsers, userService.getUsers());
    }
}
