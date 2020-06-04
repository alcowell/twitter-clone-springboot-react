package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

/**
 * This interface extends <tt>JpaRepository</tt>.
 * This interface persist <tt>User</tt> domains to the database and retrieve them.
 *
 * @author yosuk
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 *
	 * @param 	userId
	 * @return 	User 	The domain
	 */
	Optional<User> findByUserId(String userId);

	Optional<User> findByUsernameOrUserId(String username, String userId);

	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByUserId(String userId);
}
