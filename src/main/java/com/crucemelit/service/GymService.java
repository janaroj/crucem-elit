package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.GymDto;
import com.crucemelit.model.Gym;

public interface GymService extends SearchService, PictureService {

    List<GymDto> getGymsDto();

    GymDto getGymDto(long id);

    Gym getGym(long id);

}
