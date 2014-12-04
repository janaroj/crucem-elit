package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.CommentDto;
import com.crucemelit.model.Comment;

@Component
public class CommentTransformer {

    public CommentDto transformToDto(Comment comment) {
        return new CommentDto(comment);
    }

    public List<CommentDto> transformToDto(List<Comment> comments) {
        List<CommentDto> list = new ArrayList<>();
        for (Comment comment : comments) {
            list.add(transformToDto(comment));
        }
        return list;
    }

}
