package com.crucemelit.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.domain.Gender;
import com.crucemelit.domain.Role;
import com.crucemelit.model.User;

@NoArgsConstructor
public @Data class UserDto {

    private long id;

    private String email;

    private String firstName;

    private String lastName;

    private String name;

    private Double weight;

    private Integer length;

    private Date dateOfBirth;

    private Gender gender;

    private GymDto gym;

    private String token;

    private Role role;

    public UserDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.name = user.getName();
            this.weight = user.getWeight();
            this.length = user.getLength();
            this.dateOfBirth = user.getDateOfBirth();
            this.gender = user.getGender();
            this.gym = new GymDto(user.getGym());
        }
    }

}
