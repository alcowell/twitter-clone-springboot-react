package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Tweet;
import com.example.demo.payload.ApiResponce;
import com.example.demo.payload.ListResponse;
import com.example.demo.payload.TweetResponse;
import com.example.demo.repository.TweetsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.TweetService;

/**
 * Create a tweet, get a paginated list of tweets.
 * @author yosuk
 */
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

	@Autowired
	private TweetsRepository tweetRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TweetService tweetService;

	private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

	@GetMapping
	public List<TweetResponse> getAllTweet(@AuthenticationPrincipal UserPrincipal currentUser){
		return tweetService.getAllTweets(currentUser);
	}

	@GetMapping("/{userId}")
	public ListResponse<TweetResponse> getTweetByUserId(@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable Long userId){
		return tweetService.getTweetByUserId(currentUser,userId);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createTweet(@Valid @RequestParam(name = "text") String text,
			@RequestParam(name = "file", required = false) MultipartFile file){

		Tweet tweet;
		if(file != null) {
			tweet = tweetService.createTweet(text, file);
		}else {
			tweet = tweetService.createTweet(text);
		}

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{tweetId}")
				.buildAndExpand(tweet.getId()).toUri();
		return ResponseEntity.created(location)
				.body(new ApiResponce(true, "Tweet posted successfully."));
	}

	@GetMapping("/{tweetId}/like")
	@PreAuthorize("hasRole('USER')")
	public TweetResponse castLikeAndUpdateTweet(@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable Long tweetId) {
		return tweetService.castLikeAndUpdateTweet(tweetId, currentUser);
	}

	@GetMapping("/{tweetId}/notlike")
	@PreAuthorize("hasRole('USER')")
	public TweetResponse uncastLikeAndUpdateTweet(@AuthenticationPrincipal UserPrincipal currentUser,
			@PathVariable Long tweetId) {
		return tweetService.uncastLikeAndUpdateTweet(tweetId, currentUser);
	}
}
