package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.UserSummary;
import com.example.demo.repository.TweetsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.TweetService;

/**
 * Get the currently logged in user, and check if the entered information
 * of the user available for registration.
 * @author yosuk
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TweetsRepository tweetRepository;

	@Autowired
	private TweetService tweetService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(),currentUser.getUsername(),currentUser.getUserId());
		return userSummary;
	}
}
