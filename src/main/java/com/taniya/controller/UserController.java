package com.taniya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taniya.dto.UserDto;
import com.taniya.dto.mapper.UserDtoMapper;
import com.taniya.exception.UserException;
import com.taniya.model.User;
import com.taniya.service.UserService;
import com.taniya.util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    /**
     * Extract JWT token from Authorization header
     */
    private String extractToken(String jwt) {
        return jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
    }

    /**
     * Get logged-in user's profile
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt)
        throws UserException {
        User user = userService.findUserProfileByJwt(extractToken(jwt));
        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    
    /**
     * Get user details by user ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId,
        @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(extractToken(jwt));
        User user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.toUserDto(user);
        
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    
    /**
     * Search for users based on query
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query,
        @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(extractToken(jwt));
        List<User> users = userService.searchUser(query);
        List<UserDto> userDto = UserDtoMapper.toUserDtos(users);
        
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    /**
     * Update user details
     */
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User reqUserDetails,  
        @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(extractToken(jwt));
        User updatedUser = userService.updateUser(reqUser.getId(), reqUserDetails);
        UserDto userDto = UserDtoMapper.toUserDto(updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    } 
    
    /**
     * Follow a user by user ID
     */
    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> followUser(@PathVariable Long userId,
        @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(extractToken(jwt));
        User followedUser = userService.followUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.toUserDto(followedUser);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, followedUser));

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
