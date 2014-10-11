package com.crucemelit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.crucemelit.model.User;
import com.crucemelit.util.TokenUtils;

@RunWith(MockitoJUnitRunner.class)
public class TokenUtilsTest {

    private static final String EMAIL = "testemail@email.test";

    @Spy
    private TokenUtils tokenUtils;

    private User user;

    private String token;

    @Before
    public void setup() {
        user = new User();
        user.setEmail(EMAIL);
        token = tokenUtils.createToken(user);
    }

    @Test
    public void getUsernameFromTokenTest() {
        assertEquals(EMAIL, tokenUtils.getUserNameFromToken(token));
    }

    @Test
    public void getUsernameFromTokenIfNullTest() {
        assertNull(tokenUtils.getUserNameFromToken(null));
    }

    @Test
    public void validateTokenSuccessfullyTest() {
        assertTrue(tokenUtils.validateToken(token, user));
    }

    @Test
    public void validateTokenFailureTest() {
        assertFalse(tokenUtils.validateToken(token + "x", user));
        assertFalse(tokenUtils.validateToken(null, user));
        assertFalse(tokenUtils.validateToken(token, null));
    }

}
