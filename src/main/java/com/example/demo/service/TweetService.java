package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.TweetDao;
import com.example.demo.entity.Image;
import com.example.demo.entity.Like;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.ListResponse;
import com.example.demo.payload.TweetResponse;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.TweetsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
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
	private TweetDao tweetDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private LikeRepository likeRepository;

	private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

	public List<TweetResponse> getAllTweets(UserPrincipal currentUser){

		//Retrieve Tweets
//		List<Tweet> tweets = tweetsRepository.findAllAndIsLiked(currentUser.getId());
		List<TweetResponse> tweets = tweetDao.findAllTweet(currentUser.getId());

		if(tweets.isEmpty()) {
			return null;
		}

		//Map tweets to TweetResponse.
//		List<Long> tweetIds = tweets.stream().map(Tweet::getId).collect(Collectors.toList());
//		Map<Long, User> creatorMap = getTweetCreatorMap(tweets);
//		Map<Long,List<Like>> tweetLikeMap = getTweetLikeMap(tweets);
//
//		List<TweetResponse> tweetResponses = tweets.stream().map(tweet ->{
//			return ModelMapper.mapTweetToResponse(tweet,
//					creatorMap.get(tweet.getCreatedBy()),
//					tweetLikeMap.get(tweet.getId())
//					);
//			}).collect(Collectors.toList());

//		return new ListResponse<TweetResponse>(tweetResponses);
		return tweets;
	}

	public ListResponse<TweetResponse> getTweetByUserId(UserPrincipal currentUser, Long userId){
		List<Tweet> tweets = tweetsRepository.findTweetByUserIdAndIsLiked(currentUser.getId(), userId);

		if(tweets.isEmpty()) {
			return null;
		}

		//Map tweets to TweetResponse.
		List<Long> tweetIds = tweets.stream().map(Tweet::getId).collect(Collectors.toList());
		User createdUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));
		Map<Long,List<Like>> tweetLikeMap = getTweetLikeMap(tweets);

		List<TweetResponse> tweetResponses = tweets.stream().map(tweet ->{
			return ModelMapper.mapTweetToResponse(tweet,
					createdUser,
					tweetLikeMap.get(tweet.getId())
					);
			}).collect(Collectors.toList());

		return new ListResponse<TweetResponse>(tweetResponses);
	}

	public Tweet createTweet(String text, MultipartFile file) {
		Tweet tweet = new Tweet();
		Image image = imageService.storeImage(file);
		tweet.setImageId(image.getId());
		tweet.setText(text);
		return tweetDao.save(tweet);
	}

	public Tweet createTweet(String text) {
		Tweet tweet = new Tweet();
		tweet.setImageId("noId");
		tweet.setText(text);
		return tweetDao.save(tweet);
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
//		tweet.setIsLikedByCurrentUser(true);
		User likedUser = userRepository.findById(tweet.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", tweet.getCreatedBy()));
		List<Like> likes = likeRepository.findByTweetId(tweet.getId());
		return ModelMapper.mapTweetToResponse(tweet, likedUser, likes);
	}

	public TweetResponse uncastLikeAndUpdateTweet(Long tweetId, UserPrincipal currentUser) {
		Tweet tweet = tweetsRepository.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "tweet_id", tweetId));
		try {
		likeRepository.deleteByTweetIdAndUserId(tweetId, currentUser.getId());

		//ToDo: Specify type of exception.
		}catch (Exception e) {
			throw new BadRequestException("Sorry! You have already uncast your like in this tweet");
		}
		User user = userRepository.findById(tweet.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", tweet.getCreatedBy()));

//		tweet.setIsLikedByCurrentUser(false);
		List<Like> likes = likeRepository.findByTweetId(tweet.getId());

		return ModelMapper.mapTweetToResponse(tweet, user, likes);
	}

//	private void validatePageNumberAndSize(int page, int size) {
//		if(page < 0) {
//			throw new BadRequestException("Page number cannot be less than zero.");
//		}
//
//		if(size > AppConstants.MAX_PAGE_SIZE) {
//			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
//		}
//	}

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
