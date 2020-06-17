package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Like;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.payload.TweetResponse;
import com.example.demo.payload.UserSummary;

/**
 * This class map the entity to response payload.
 * @author yosuk
 */
public class ModelMapper {

	public static TweetResponse mapTweetToResponse(Tweet tweet, User creator, List<Like> likes) {
		TweetResponse tweetResponse = new TweetResponse();
		tweetResponse.setId(tweet.getId());
		tweetResponse.setText(tweet.getText());
		tweetResponse.setCreationDateTime(tweet.getCreatedAt());

		List<UserSummary> users = new ArrayList<UserSummary>();
		if(!likes.isEmpty()) {
			for(Like oneLike : likes) {
				User user = oneLike.getUser();
				users.add(new UserSummary(user.getId(),user.getUsername(),user.getUserId()));
			}
			tweetResponse.setLikedUsers(users);
		}else {
			tweetResponse.setLikedUsers(null);
		}
		//ToDo: Add delete function
		tweetResponse.setIsDeleted(false);

		UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getUserId());
		tweetResponse.setCreatedBy(creatorSummary);

		return tweetResponse;
	}
}
