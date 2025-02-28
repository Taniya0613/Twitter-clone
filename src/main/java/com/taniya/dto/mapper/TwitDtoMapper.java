package com.taniya.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.taniya.dto.TwitDto;
import com.taniya.dto.UserDto;
import com.taniya.model.Twit;
import com.taniya.model.User;
import com.taniya.util.TwitUtil;

public class TwitDtoMapper {

    public static TwitDto toTwitDto(Twit twit, User reqUser) {
        if (twit == null) return null;

        UserDto user = UserDtoMapper.toUserDto(twit.getUser());

        boolean isLiked = reqUser != null && TwitUtil.isLikedByReqUser(reqUser, twit);
        boolean isRetwited = reqUser != null && TwitUtil.isRetwitedByReqUser(reqUser, twit);

        List<User> retwitUsers = twit.getRetwitUser() != null ? twit.getRetwitUser() : Collections.emptyList();
        List<Long> retwitUserId = retwitUsers.stream().map(User::getId).collect(Collectors.toList());

        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes() != null ? twit.getLikes().size() : 0);
        twitDto.setTotalReplies(twit.getReplyTwits() != null ? twit.getReplyTwits().size() : 0);
        twitDto.setTotalRetweets(retwitUsers.size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setRetwit(isRetwited);
        twitDto.setRetwitUsersId(retwitUserId);
        twitDto.setReplyTwits(twit.getReplyTwits() != null ? toTwitDtos(twit.getReplyTwits(), reqUser) : Collections.emptyList());
        twitDto.setVideo(twit.getVideo());

        return twitDto;
    }

    public static List<TwitDto> toTwitDtos(List<Twit> twits, User reqUser) {
        if (twits == null) return Collections.emptyList();
        return twits.stream().map(twit -> toTwitDto(twit, reqUser)).collect(Collectors.toList());
    }
}
