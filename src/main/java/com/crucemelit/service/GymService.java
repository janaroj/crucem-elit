package com.crucemelit.service;

import java.util.List;

import com.crucemelit.model.Gym;

public interface GymService extends SearchService, PictureService {

    List<Gym> getGyms();

    Gym getGym(long id);

}
