package com.taniya.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.taniya.dto.UserDto;
import com.taniya.model.User;

public class UserDtoMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) return null; // Prevents NullPointerException

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setImage(user.getImage());
        userDto.setBackgroundImage(user.getBackgroundImage());
        userDto.setBio(user.getBio());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setFollowers(user.getFollowers() != null ? toUserDtos(user.getFollowers()) : Collections.emptyList());
        userDto.setFollowing(user.getFollowings() != null ? toUserDtos(user.getFollowings()) : Collections.emptyList());
        userDto.setLogin_with_google(user.isLogin_with_google());
        userDto.setLocation(user.getLocation());

        return userDto;
    }

    public static List<UserDto> toUserDtos(List<User> users) {
        return users == null ? Collections.emptyList() :
            users.stream().map(UserDtoMapper::toUserDto).collect(Collectors.toList());
    }
}
