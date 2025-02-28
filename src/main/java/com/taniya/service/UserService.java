package com.taniya.service;

import java.util.List;

import com.taniya.exception.UserException;
import com.taniya.model.User;

public interface UserService {
	
	public User findUserById(Long userId)throws UserException;
	
	public UserException findUserProfileByJwt(String jwt)throws UserException;
	
	public User updateUser(Long userId, User user)throws UserException;
	
	public User folloUser(Long userId,UserException user)throws UserException;
	
	public List<User> searUser(String query);

}
