package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.helper.QueryBuilder;
import com.example.demo.entity.Tweet;
import com.example.demo.payload.TweetResponse;

/**
 * @author yosuk
 *
 */
@Repository
@Transactional
public class TweetDao {

	@PersistenceContext
	EntityManager em;

	public List<TweetResponse> findAllTweet(Long currentUserId) {
		QueryBuilder queryBuilder = new QueryBuilder(em);
		queryBuilder.append("select t.id, t.created_at, t.text, count(l2.id) as like_num, ");
		queryBuilder.append("tweet_image.path, u.id as created_by_id, u.username as created_by_name, u.user_id as created_by_user_id, ");
		queryBuilder.append("(case when (l1.tweet_id is null) then false ");
		queryBuilder.append("else true end) as is_liked from tweets t ");
		queryBuilder.append("left join likes l1 on t.id = l1.tweet_id ");
		queryBuilder.append("and l1.user_id = :userId ").setParam("userId", currentUserId);
		queryBuilder.append("left join likes l2 on t.id = l2.tweet_id ");
		queryBuilder.append("left join image tweet_image on t.image_id = tweet_image.id ");
		queryBuilder.append("left join users u on t.created_by = u.id ");
		queryBuilder.append("group by t.id");
		queryBuilder.createQuery(TweetResponse.class);

		return queryBuilder.findResultList();
	}

	public Tweet save(Tweet tweet) {
		return em.merge(tweet);
	}
}
