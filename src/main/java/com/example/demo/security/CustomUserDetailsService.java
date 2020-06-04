package com.example.demo.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
/**
 * This class calls the repository with the user's Id and name as arguments.
 * This class is custom <tt>UserDetailService</tt> for user authentication.
 *
 * <p>The methods of this class all throws a <tt>UsernameNotFoundException</tt>
 * if an unprivileged user trys to login.
 *
 * @author yosuk
 * @see UserRepository
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Let users login with either username or userId
	 * @param usernameOrUserId The string represents user's name or id.
	 * @throws UsernameNotFoundException Exception thrown when the UserDetailsService
	 *                                    implementation cannot find a User by username.
	 * @return UserPrincipal The class store the information object to perform
	 *                        authentication and authorization.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrUserId) throws UsernameNotFoundException {
		//
		User user = userRepository.findByUsernameOrUserId(usernameOrUserId, usernameOrUserId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or userId :" + usernameOrUserId)
				);
		return UserPrincipal.create(user);
	}

	/**
	 * Find user information
	 * @param id
	 * @return UserPrincipal The class store the information object to perform authentication and authorization.
	 */
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id :" + id)
				);
		return UserPrincipal.create(user);
	}


}
