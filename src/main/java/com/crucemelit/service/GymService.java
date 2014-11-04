package com.crucemelit.service;

import java.util.List;

import com.crucemelit.model.Gym;

public interface GymService extends SearchService {

    List<Gym> getGyms();

    Gym getGym(long id);

    void setGymPicture(byte[] bytesFromStream, long id);

    String getGymPicture(long id);

}
