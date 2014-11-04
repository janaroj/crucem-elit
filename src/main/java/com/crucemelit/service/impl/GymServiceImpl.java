package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;
import com.crucemelit.service.GymService;
import com.crucemelit.util.Utility;

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

    @Override
    public List<Suggestion> search(String term) {
        return Utility.getSuggestions(gymRepository.findBySearchTerm(term), SuggestionType.GYM);
    }

    @Override
    public void setGymPicture(byte[] picture, long id) {
        Gym gym = getGym(id);
        gym.setPicture(picture);
        gymRepository.saveAndFlush(gym);

    }

    @Override
    public String getGymPicture(long id) {
        return Utility.getImgSourceFromBytes(getGym(id).getPicture());
    }

}
