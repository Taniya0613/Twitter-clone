package com.taniya.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.taniya.dto.LikeDto;
import com.taniya.dto.TwitDto;
import com.taniya.dto.UserDto;
import com.taniya.model.Like;
import com.taniya.model.User;

public class LikeDtoMapper {

    public static LikeDto toLikeDto(Like like, User reqUser) {
        UserDto user = UserDtoMapper.toUserDto(like.getUser());
        TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTwit(twit);
        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser) {  // ✅ Fixed method name
        List<LikeDto> likeDtos = new ArrayList<>();

        for (Like like : likes) {  // ✅ Fixed variable naming
            UserDto user = UserDtoMapper.toUserDto(like.getUser());
            TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setTwit(twit);
            likeDto.setUser(user);

            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
