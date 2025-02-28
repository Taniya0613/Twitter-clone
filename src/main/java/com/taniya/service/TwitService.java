package com.taniya.service;

import java.util.List;

import com.taniya.exception.TwitException;
import com.taniya.exception.UserException;
import com.taniya.model.Twit;
import com.taniya.model.User;
import com.taniya.request.TwitReplyRequest;

public interface TwitService {

	
	public Twit createTwit(Twit req,User user)throws UserException;
	public List<Twit> findAllTwits();
	public Twit retwit(Long twitId,User user)throws UserException,TwitException;
	public Twit findById(Long twitId)throws TwitException;
	
	public void deleteTwitById(Long twitId,Long userId)throws TwitException,UserException;

	public Twit removeFromRetwit(Long twitId,User user)throws TwitException,UserException;
	
	public Twit createdReply(TwitReplyRequest req,User user)throws TwitException;
	
	public List<Twit> getUserTwit(User user);
	
	public List<Twit> findByLikesContainsUser(User user);
	
}
