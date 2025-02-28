package com.taniya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taniya.dto.TwitDto;
import com.taniya.dto.mapper.TwitDtoMapper;
import com.taniya.exception.TwitException;
import com.taniya.exception.UserException;
import com.taniya.model.Twit;
import com.taniya.model.User;
import com.taniya.request.TwitReplyRequest;
import com.taniya.response.ApiResponse;
import com.taniya.service.TwitService;
import com.taniya.service.UserService;

@RestController
@RequestMapping("/api/twits")
public class TwitController {

    @Autowired
    private TwitService twitService;

    @Autowired
    private UserService userService;

    private String extractToken(String jwt) {
        return jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
    }

    @PostMapping("/create")
    public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req,
                                              @RequestHeader("Authorization") String jwt) 
                                              throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        Twit twit = twitService.createTwit(req, user);
        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest req,
                                             @RequestHeader("Authorization") String jwt) 
                                             throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        Twit twit = twitService.createReply(req, user);
        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PutMapping("/{twitId}/retwit")
    public ResponseEntity<TwitDto> retwit(@PathVariable Long twitId,
                                          @RequestHeader("Authorization") String jwt) 
                                          throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        Twit twit = twitService.retwit(twitId, user);
        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @GetMapping("/{twitId}")
    public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId,
                                                @RequestHeader("Authorization") String jwt) 
                                                throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        Twit twit = twitService.findById(twitId);
        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @DeleteMapping("/{twitId}")
    public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId,
                                                  @RequestHeader("Authorization") String jwt) 
                                                  throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        twitService.deleteTwitById(twitId, user.getId());

        ApiResponse res = new ApiResponse("Twit deleted Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization") String jwt) 
                                                     throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(extractToken(jwt));
        List<Twit> twits = twitService.findAllTwit();
        List<TwitDto> twitDtos = TwitDtoMapper.toTwitDtos(twits, user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId,  
                                                                     @RequestHeader("Authorization") String jwt) 
                                                                     throws UserException, TwitException {

        userService.findUserProfileByJwt(extractToken(jwt)); // Only verifying JWT
        List<Twit> twits = twitService.findByLikesContainingUser(userId);
        List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twits, null);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }
}
