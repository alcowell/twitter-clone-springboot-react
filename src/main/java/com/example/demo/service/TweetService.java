package com.example.demo.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Like;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.PagedResponse;
import com.example.demo.payload.TweetRequest;
import com.example.demo.payload.TweetResponse;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.TweetsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.util.AppConstants;
import com.example.demo.util.ModelMapper;

/**
 * @author yosuk
 *
 */
@Service
public class TweetService {

	@Autowired
	private TweetsRepository tweetsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeRepository likeRepository;

	private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

	public PagedResponse<TweetResponse> getAllTweets(UserPrincipal currentUser, int page, int size){

		validatePageNumberAndSize(page, size);

		//Retrieve Tweets
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Tweet> tweets = tweetsRepository.findAll(pageable);

		if(tweets.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tweets.getNumber(),
					tweets.getSize(), tweets.getTotalElements(), tweets.getTotalPages(), tweets.isLast());
		}

		//Map tweets to TweetResponse.
		List<Long> tweetIds = tweets.map(Tweet::getId).getContent();
		Map<Long, User> creatorMap = getTweetCreatorMap(tweets.getContent());
		Map<Long,List<Like>> tweetLikeMap = getTweetLikeMap(tweets.getContent());

		List<TweetResponse> tweetReponses = tweets.map(tweet ->{
			return ModelMapper.mapTweetToResponse(tweet,
					creatorMap.get(tweet.getCreatedBy()),
					tweetLikeMap.get(tweet.getId())
					);
			}).getContent();

		return new PagedResponse<>(tweetReponses, tweets.getNumber(),
				tweets.getSize(), tweets.getTotalElements(), tweets.getTotalPages(), tweets.isLast());
	}

	public Tweet createTweet(TweetRequest tweetRequest) {
		Tweet tweet = new Tweet();
		tweet.setText(tweetRequest.getText());
		return tweetsRepository.save(tweet);
	}

	public TweetResponse castLikeAndUpdateTweet(Long tweetId, UserPrincipal currentUser) {
		Tweet tweet = tweetsRepository.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "tweet_id", tweetId));
		User user = userRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", currentUser.getId()));
		Like like = new Like();
		like.setTweet(tweet);
		like.setUser(user);

		try {
			like = likeRepository.save(like);
		}catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Sorry! You have already cast your like in this tweet");
		}

		List<Like> likes = likeRepository.findByTweetId(tweet.getId());

		return ModelMapper.mapTweetToResponse(tweet, user, likes);
	}

	public TweetResponse uncastLikeAndUpdateTweet(Long tweetId, UserPrincipal currentUser) {
		Tweet tweet = tweetsRepository.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "tweet_id", tweetId));
		User user = userRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", currentUser.getId()));

		try {
		likeRepository.deleteByTweetIdAndUserId(tweetId, currentUser.getId());

		//ToDo: Specify type of exception.
		}catch (Exception e) {
			throw new BadRequestException("Sorry! You have already uncast your like in this tweet");
		}

		List<Like> likes = likeRepository.findByTweetId(tweet.getId());

		return ModelMapper.mapTweetToResponse(tweet, user, likes);
	}

	private void validatePageNumberAndSize(int page, int size) {
		if(page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if(size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	/**
	 * Get Tweet Creator details of the given list of tweets.
	 * @param tweets
	 * @return
	 */
	private Map<Long,User> getTweetCreatorMap(List<Tweet> tweets){

		List<Long> creatorIds = tweets.stream()
			.map(Tweet::getCreatedBy)
			.distinct()
			.collect(Collectors.toList());

		List<User> creators = userRepository.findByIdIn(creatorIds);
		Map<Long, User> creatorMap = creators.stream()
				.collect(
						Collectors.toMap(User::getId, Function.identity())
						);
		return creatorMap;
	}

	private Map<Long,List<Like>> getTweetLikeMap(List<Tweet> tweets){

		Map<Long,List<Like>> tweetLikeMap = new HashMap<Long, List<Like>>();
		for(Tweet tweet : tweets) {
			tweetLikeMap.put(tweet.getId(), likeRepository.findByTweetId(tweet.getId()));
		}
		return tweetLikeMap;
	}

}
