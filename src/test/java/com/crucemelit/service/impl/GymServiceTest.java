package com.crucemelit.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;

@RunWith(MockitoJUnitRunner.class)
public class GymServiceTest {

    private static final long EXISTING_GYM_ID = 1;
    private static final long NOT_EXISTING_GYM_ID = 2;

    @Mock
    private GymRepository gymRepository;

    @Mock
    private Gym mockGym;

    @Spy
    @InjectMocks
    private GymServiceImpl gymService;

    private List<Gym> mockGyms;

    @Before
    public void setUp() {
        mockGyms = new ArrayList<>();
        mockGyms.add(mockGym);
        when(gymRepository.findAll()).thenReturn(mockGyms);
        when(gymRepository.findOne(EXISTING_GYM_ID)).thenReturn(mockGym);
    }

    @Test
    public void getGymsTest() {
        assertEquals(mockGyms, gymService.getAllGyms());
    }

    @Test
    public void getGymSuccessfullyTest() {
        assertEquals(mockGym, gymService.getGym(EXISTING_GYM_ID));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getGymFailureTest() {
        gymService.getGym(NOT_EXISTING_GYM_ID);
    }

}
