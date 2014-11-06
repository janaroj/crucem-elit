package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.GymDto;
import com.crucemelit.model.Gym;

@Component
public class GymTransformer {

    public GymDto transformToDto(Gym gym) {
        return new GymDto(gym);
    }

    public List<GymDto> transformToDto(List<Gym> gyms) {
        List<GymDto> list = new ArrayList<>();
        for (Gym gym : gyms) {
            list.add(transformToDto(gym));
        }
        return list;
    }

}
