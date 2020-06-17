package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Like;

/**
 * @author yosuk
 *
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("SELECT * FROM likes WHERE likes.tweet_id = :tweetId")
	List<Like> findByTweetId(@Param("tweetId") Long tweetId);

	@Query("SELECT * FROM likes WHERE likes.user_id = :userId")
	List<Like> findByUserId(@Param("userId") Long userId);

	@Query("SELECT * FROM likes WHERE likes.tweet_id = :tweetId AND likes.user_id = :userId")
	Optional<Like> findByTweetIdAndUserId(@Param("tweetId")Long tweetId,@Param("userId") Long userId);
}
