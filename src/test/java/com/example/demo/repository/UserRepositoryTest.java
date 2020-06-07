package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import com.example.demo.entity.User;

/**
 * @author yosuk
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	void beforeEach() {
		Role role = new Role(RoleName.ROLE_USER);
		User user1 = new User(
				"alcowell@example.com",
				"alcowell",
				"alcowell8"
				);

		user1.setRoles(Collections.singleton(role));
		entityManager.persist(user1);
	}

	/**
	 * Normal usecase for findByUserId().
	 */
	@Test
	void testFindByUserId1() {
		String userId = "alcowell@example.com";
		User actualUser = userRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
		User expectedUser = new User(
				"alcowell@example.com",
				"alcowell",
				"alcowell8"
				);
		expectedUser.setId((long)1);
		assertEquals(expectedUser.getId(),actualUser.getId(),"Idの比較");
		assertEquals(expectedUser.getUserId(),actualUser.getUserId(),"UserIdの比較");
		assertEquals(expectedUser.getUsername(),actualUser.getUsername(),"Usernameの比較");
		assertEquals(expectedUser.getPassword(),actualUser.getPassword(),"Passwordの比較");
	}

	/**
	 * Exception usecase for findByUserId().
	 * Scenario : Unregistered user id is entered. And UsernameNotFoundException is calld.
	 */
	@Test
	void testFindByUserId2() {
		String userId = "aaa@example.com";
//		User actualUser = userRepository.findByUserId(userId)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
		assertThrows(UsernameNotFoundException.class, () -> userRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId)));
}


	/**
	 * Normal usecase for findByUsernameOrUserId().
	 */
	@Test
	void findByUsernameOrUserId() {
		String userId = "alcowell@example.com";
		String username = "alcowell";
		User actualUser1 = userRepository.findByUsernameOrUserId(userId,userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
		User actualUser2 = userRepository.findByUsernameOrUserId(username,username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		User expectedUser = new User(
				"alcowell@example.com",
				"alcowell",
				"alcowell8"
				);
		expectedUser.setId((long)1);

		assertEquals(expectedUser.getId(),actualUser1.getId(),"Idの比較");
		assertEquals(expectedUser.getUserId(),actualUser1.getUserId(),"UserIdの比較");
		assertEquals(expectedUser.getUsername(),actualUser1.getUsername(),"Usernameの比較");
		assertEquals(expectedUser.getPassword(),actualUser1.getPassword(),"Passwordの比較");

		assertEquals(expectedUser.getId(),actualUser2.getId(),"Idの比較");
		assertEquals(expectedUser.getUserId(),actualUser2.getUserId(),"UserIdの比較");
		assertEquals(expectedUser.getUsername(),actualUser2.getUsername(),"Usernameの比較");
		assertEquals(expectedUser.getPassword(),actualUser2.getPassword(),"Passwordの比較");
	}

}
