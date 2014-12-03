package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.CommentDto;
import com.crucemelit.dto.GymDto;
import com.crucemelit.model.Gym;

public interface GymService extends SearchService, PictureService {

    List<GymDto> getGymDtos();

    GymDto getGymDto(long id);

    Gym getGym(long id);

    void createGym(Gym gym);

    void deleteGym(long id);

    void updateGym(Gym gym);

    List<CommentDto> getGymComments(long id);

}
