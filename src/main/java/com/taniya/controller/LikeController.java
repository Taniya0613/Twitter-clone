package com.taniya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taniya.dto.LikeDto;
import com.taniya.dto.mapper.LikeDtoMapper;
import com.taniya.exception.TwitException;
import com.taniya.exception.UserException;
import com.taniya.model.Like;
import com.taniya.model.User;
import com.taniya.service.LikeService;
import com.taniya.service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LikeService likeService;

    private String extractToken(String jwt) {
        return jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
    }
    
    @PostMapping("/{twitId}/likes")
    public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId,
        @RequestHeader("Authorization") String jwt) throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        Like like = likeService.likeTwit(twitId, user);
        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);

        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }
    
    @GetMapping("/twit/{twitId}/likes")  // ✅ Changed to GET request
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId,
        @RequestHeader("Authorization") String jwt) throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        List<Like> likes = likeService.getAllLikes(twitId);
        List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);

        return new ResponseEntity<>(likeDtos, HttpStatus.OK);
    }
}
