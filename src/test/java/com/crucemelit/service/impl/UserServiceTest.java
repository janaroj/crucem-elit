package com.crucemelit.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crucemelit.dto.UserDto;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.exception.UserAlreadyExistsException;
import com.crucemelit.model.User;
import com.crucemelit.repository.UserRepository;
import com.crucemelit.transformer.UserTransformer;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String EXISTING_EMAIL = "existing@email.test";
    private static final String NOT_EXISTING_EMAIL = "notexisting@email.test";
    private static final long EXISTING_USER_ID = 1;
    private static final long NOT_EXISTING_USER_ID = 2;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    private List<User> mockUsers;

    @Spy
    private UserTransformer userTransformer;

    @Mock
    private User mockUser;

    @Mock
    private User mockFriendOne;
    @Mock
    private User mockFriendTwo;
    @Mock
    private User mockGymContact;

    @Mock
    private Authentication authentication;

    @Before
    public void setUp() {
        mockUsers = Arrays.asList(mockUser, mockGymContact, mockFriendOne, mockFriendTwo);
        mockRepository();
    }

    private void mockRepository() {
        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL)).thenReturn(mockUser);
        when(userRepository.findOne(EXISTING_USER_ID)).thenReturn(mockUser);
    }

    @Test
    public void getUserSuccessfullyTest() {
        assertEquals(mockUser, userService.getUser(EXISTING_USER_ID));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserFailureTest() {
        userService.getUser(NOT_EXISTING_USER_ID);
    }

    @Test
    public void loadUserByUsernameExistingTest() {
        assertEquals(mockUser, userService.loadUserByUsername(EXISTING_EMAIL));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotExistingTest() {
        userService.loadUserByUsername(NOT_EXISTING_EMAIL);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNullTest() {
        userService.loadUserByUsername(null);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void registerAlreadyExistingUsernameTest() {
        when(mockUser.getEmail()).thenReturn(EXISTING_EMAIL);
        userService.register(mockUser);
    }

    @Test
    public void getContactsTest() {
        mockAuthentication();
        List<User> gymContacts = Arrays.asList(mockUser, mockGymContact, mockFriendOne);
        List<User> friends = Arrays.asList(mockFriendOne, mockFriendTwo);
        when(mockUser.getContactsFromGym()).thenReturn(gymContacts);
        when(mockUser.getFriends()).thenReturn(friends);
        List<User> expectedContacts = Arrays.asList(mockGymContact, mockFriendTwo, mockFriendOne);
        List<UserDto> actualContacts = userService.getContactsDto();
        assertTrue(listsAreEqual(userTransformer.transformToDto(expectedContacts), actualContacts));
    }

    private void mockAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(((User) authentication.getPrincipal()).getId()).thenReturn(EXISTING_USER_ID);
    }

    private boolean listsAreEqual(List<UserDto> expectedContacts, List<UserDto> actualContacts) {
        return actualContacts.size() == expectedContacts.size() && expectedContacts.containsAll(actualContacts)
                && actualContacts.containsAll(expectedContacts);
    }

}
