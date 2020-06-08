package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.payload.PagedResponse;
import com.example.demo.payload.TweetResponse;
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

		List<TweetResponse> tweetReponses = tweets.map(tweet ->{
			return ModelMapper.mapTweetToResponse(tweet,
					creatorMap.get(tweet.getCreatedBy()));
			}).getContent();

		return new PagedResponse<>(tweetReponses, tweets.getNumber(),
				tweets.getSize(), tweets.getTotalElements(), tweets.getTotalPages(), tweets.isLast());
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

}
