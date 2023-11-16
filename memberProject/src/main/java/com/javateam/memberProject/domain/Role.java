package com.javateam.memberProject.domain;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 7464267597005842862L;
	
	private String username;
	private String role;

	// 추가
	public Role() {}
	
	// 추가
	public Role(String username, String role) {
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

	@Override
	public String toString() {
		return "Role [username=" + username + ", role=" + role + "]";
	}
	
}
