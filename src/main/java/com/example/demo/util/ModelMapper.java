package com.example.demo.util;

import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.payload.TweetResponse;
import com.example.demo.payload.UserSummary;

/**
 * This class map the entity to response payload.
 * @author yosuk
 */
public class ModelMapper {

	public static TweetResponse mapTweetToResponse(Tweet tweet, User creator) {
		TweetResponse tweetResponse = new TweetResponse();
		tweetResponse.setId(tweet.getId());
		tweetResponse.setText(tweet.getText());
		tweetResponse.setCreationDateTime(tweet.getCreatedAt());

		//ToDo: Add delete function
		tweetResponse.setIsDeleted(false);

		UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getUserId());
		tweetResponse.setCreatedBy(creatorSummary);

		return tweetResponse;
	}
}
