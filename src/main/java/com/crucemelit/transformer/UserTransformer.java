package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.UserDto;
import com.crucemelit.model.User;

@Component
public class UserTransformer {

    public UserDto transformToDto(User user) {
        return new UserDto(user);
    }

    public List<UserDto> transformToDto(Collection<User> users) {
        List<UserDto> list = new ArrayList<>();
        for (User user : users) {
            list.add(transformToDto(user));
        }
        return list;
    }

    public UserDto transformToDtoWithAuthInfo(User user) {
        UserDto userDto = transformToDto(user);
        userDto.setToken(user.getToken());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public List<UserDto> transformToDtoWithAuthInfo(Collection<User> users) {
        List<UserDto> list = new ArrayList<>();
        for (User user : users) {
            list.add(transformToDtoWithAuthInfo(user));
        }
        return list;
    }
}
