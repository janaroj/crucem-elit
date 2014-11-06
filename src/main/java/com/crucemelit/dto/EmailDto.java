package com.crucemelit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;

@NoArgsConstructor
public @Data class EmailDto {

    @Email
    private String email;

}
