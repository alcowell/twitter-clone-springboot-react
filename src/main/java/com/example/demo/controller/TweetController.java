package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.PagedResponse;
import com.example.demo.payload.TweetResponse;
import com.example.demo.repository.TweetsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TweetService;

/**
 * @author yosuk
 *
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
	public PagedResponse<TweetResponse> getTweets(@Princi)
}
