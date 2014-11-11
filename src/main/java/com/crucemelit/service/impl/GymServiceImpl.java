package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.GymDto;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.model.Gym;
import com.crucemelit.repository.GymRepository;
import com.crucemelit.service.GymService;
import com.crucemelit.transformer.GymTransformer;
import com.crucemelit.util.Utility;

@Service
@Transactional
public class GymServiceImpl implements GymService {

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private GymTransformer gymTransformer;

    List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    @Override
    public List<GymDto> getGymsDto() {
        return gymTransformer.transformToDto(getAllGyms());
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
    public GymDto getGymDto(long id) {
        return gymTransformer.transformToDto(getGym(id));
    }

    @Override
    public List<Suggestion> search(String term) {
        return Utility.getSuggestions(gymRepository.findBySearchTerm(term), SuggestionType.GYM);
    }

    @Override
    public void setPicture(byte[] picture, long... id) {
        Gym gym = getGym(id[0]);
        gym.setPicture(picture);
        gymRepository.saveAndFlush(gym);
    }

    @Override
    public String getPicture(long id) {
        byte[] pictureBytes = getGym(id).getPicture();
        if (pictureBytes == null) {
            return "";
        }
        return Utility.getImgSourceFromBytes(pictureBytes);
    }

}
