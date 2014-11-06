package com.crucemelit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Gym;

@NoArgsConstructor
public @Data class GymDto {

    private long id;

    private String name;

    private String address;

    private String city;

    private String trainers;

    private String contact;

    private String fullAddress;

    public GymDto(Gym gym) {
        if (gym != null) {
            this.id = gym.getId();
            this.name = gym.getName();
            this.address = gym.getAddress();
            this.city = gym.getCity();
            this.trainers = gym.getTrainers();
            this.contact = gym.getContact();
            this.fullAddress = gym.getFullAddress();
        }
    }

}
