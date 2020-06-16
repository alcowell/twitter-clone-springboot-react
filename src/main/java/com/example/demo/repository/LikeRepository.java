package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Like;

/**
 * @author yosuk
 *
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

}
