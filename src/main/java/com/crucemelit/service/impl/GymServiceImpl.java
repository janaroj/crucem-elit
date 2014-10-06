package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;
import com.crucemelit.service.GymService;

@Service
public class GymServiceImpl implements GymService {

    @Autowired
    private GymRepository gymRepository;

    @Override
    public List<Gym> getGyms() {
        return gymRepository.findAll();
    }

}