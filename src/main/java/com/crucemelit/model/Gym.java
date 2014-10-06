package com.crucemelit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "GYM")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class Gym extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String address;

    private String city;

    private String trainers;

    private String contact;

    @OneToMany(mappedBy = "gym", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<User> users;

    public String getFullAddress() {
        return String.format("%s, %s", getCity(), getAddress());
    }

}
