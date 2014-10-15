package com.crucemelit.dto;

import lombok.Data;

import org.hibernate.validator.constraints.Email;

public @Data class EmailDto {

    @Email
    private String email;

}
