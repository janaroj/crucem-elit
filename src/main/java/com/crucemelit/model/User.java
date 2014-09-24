package com.crucemelit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class User {

    @Id
    private long id;

    private String name;

    public User(String name) {
        this.name = name;
    }

}
