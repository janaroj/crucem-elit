package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.Suggestion;
import com.crucemelit.model.Gym;

public interface GymService {

    List<Gym> getGyms();

    Gym getGym(long id);

	List<Suggestion> search(String term);

}
