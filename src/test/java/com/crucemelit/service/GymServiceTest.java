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

import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;
import com.crucemelit.service.impl.GymServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GymServiceTest {

    @Mock
    private GymRepository gymRepository;

    @Spy
    @InjectMocks
    private GymServiceImpl gymService;

    private List<Gym> mockGyms;

    @Before
    public void setUp() {
        when(gymRepository.findAll()).thenReturn(mockGyms);
    }

    @Test
    public void getGymsTest() {
        assertEquals(mockGyms, gymService.getGyms());
    }

}
