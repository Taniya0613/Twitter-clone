package com.taniya.service;

import java.util.List;

import com.taniya.exception.TwitException;
import com.taniya.exception.UserException;
import com.taniya.model.Like;
import com.taniya.model.User;

public interface LikeService {

	public Like likeTwit(Long twitId,User user)throws UserException,TwitException;
	
	public List<Like>getAllLikes(Long twitId)throws TwitException;
}
