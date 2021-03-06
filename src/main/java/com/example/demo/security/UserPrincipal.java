package com.example.demo.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is custom <tt>UserDetails</tt>.Instance of this class will be
 * returned from our custom <tt>UserDetailService</tt>. Spring Security will
 * use the information stored in this class object to perform authentication
 * and authorization.
 *
 *
 * @author yosuk
 * @version 1.0
 */
public class UserPrincipal implements UserDetails {

	/**
	 * A long number that generated by our API for identifying  users.
	 */
	private Long id;

	/**
	 *  A string that created by users in order to name themselves.
	 */
	private String username;

	/**
	 * A string that created by users for identifying themselves.
	 */
	@JsonIgnore
	private String userId;

	/**
	 * A string that created by users.
	 */
	@JsonIgnore
	private String password;

	/**
	 * A collection of types of authorities.
	 */
	private Collection<? extends GrantedAuthority> authorities;


	/**
	 * Constructs an <code>UserPrincipal</code> object.
	 * @param id 			A long number that generated by our API for identifying  users.
	 * @param username		A string that created by users in order to name themselves.
	 * @param userId		A string that created by users for identifying themselves.
	 * @param password		A string that created by users.
	 * @param authorities	A collection of types of authorities.
	 */
	public UserPrincipal(Long id, String username, String userId, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.authorities = authorities;
	}

	/**
	 * Create authenticated user based on <tt>User</tt> which represents user
	 * trying login.
	 * @param user's information who trying to login.
	 * @return authorized user information.
	 */
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
				new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserPrincipal(
				user.getId(),
				user.getUsername(),
				user.getUserId(),
				user.getPassword(),
				authorities
			);
	}

	public Long getId() {
		return this.id;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean equals(Object o) {
		if(this == o)return true;
		if(o == null || getClass() != o.getClass()) return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(this.id, that.id);
	}

	public int hashCode() {

		return Objects.hash(id);
	}

}
