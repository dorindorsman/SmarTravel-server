package smarTravel.users;

import smarTravel.DomainWithEmail;

public class UserBoundary {

	private DomainWithEmail userId;
	private String role;
	private String username;
	private String avatar;

	public UserBoundary() {
	}

	public UserBoundary(DomainWithEmail userId) {
		this.userId = userId;
	}
	
	public DomainWithEmail getUserId() {
		return userId;
	}

	public void setUserId(DomainWithEmail userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
