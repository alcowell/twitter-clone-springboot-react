package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Tweet;

/**
 * @author yosuk
 *
 */
@Repository
public interface TweetsRepository extends JpaRepository<Tweet, Long> {


	Optional<Tweet> findById(Long tweetId);

	Page<Tweet> findByCreatedBy(Long userId, Pageable pageble);

	long countByCreatedBy(Long userId);

	List<Tweet> findByIdIn(List<Long> tweetIds);

	List<Tweet> findByIdIn(List<Long> tweetIds,Sort sort);

	@Query(value = "select t.id,t.created_at,t.update_at,t.created_by, "
			+ "t.update_by,t.text, "
			+ "(case when (likes.tweet_id is null) then false "
			+ "else true end) as is_liked_by_current_user from tweets t "
			+ "left join likes on t.id = likes.tweet_id "
			+ "and likes.user_id = :userId "
			+ "order by created_at desc",nativeQuery = true)
	List<Tweet> findAllAndIsLiked(@Param("userId") Long userId);
}
