package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Image;

/**
 * @author yosuk
 *
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

}
