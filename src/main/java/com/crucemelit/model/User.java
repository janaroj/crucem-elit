package com.crucemelit.model;

import lombok.Data;

public @Data class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

}
