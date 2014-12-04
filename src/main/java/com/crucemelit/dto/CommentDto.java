package com.crucemelit.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Comment;

@NoArgsConstructor
public @Data class CommentDto {

    private long id;

    private String text;

    private UserDto user;

    private Date date;

    public CommentDto(Comment comment) {
        if (comment != null) {
            this.id = comment.getId();
            this.text = comment.getText();
            this.date = comment.getDate();
            this.user = new UserDto(comment.getUser());
        }
    }

}
