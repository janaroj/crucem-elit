package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;
import com.crucemelit.service.GymService;

@Service
@Transactional
public class GymServiceImpl implements GymService {

    @Autowired
    private GymRepository gymRepository;

    @Override
    public List<Gym> getGyms() {
        return gymRepository.findAll();
    }

    @Override
    public Gym getGym(long id) {
        Gym gym = gymRepository.findOne(id);
        if (gym == null) {
            throw new EntityNotFoundException();
        }
        return gym;
    }

}
