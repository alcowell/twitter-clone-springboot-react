package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
