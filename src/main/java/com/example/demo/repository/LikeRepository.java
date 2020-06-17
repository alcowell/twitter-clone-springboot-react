package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Like;

/**
 * @author yosuk
 *
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "SELECT * FROM likes WHERE likes.tweet_id = :tweetId",nativeQuery = true)
	List<Like> findByTweetId(@Param("tweetId") Long tweetId);

	@Query(value = "SELECT * FROM likes WHERE likes.user_id = :userId", nativeQuery = true)
	List<Like> findByUserId(@Param("userId") Long userId);

	@Query(value = "SELECT * FROM likes WHERE likes.tweet_id = :tweetId AND likes.user_id = :userId", nativeQuery = true)
	Like findByTweetIdAndUserId(@Param("tweetId")Long tweetId,@Param("userId") Long userId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM likes WHERE likes.tweet_id = :tweetId AND likes.user_id = :userId",nativeQuery = true)
	void deleteByTweetIdAndUserId(@Param("tweetId")Long tweetId, @Param("userId") Long userId);
}
