package iob.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
USERS_TABLE
---------------------------
//USER_EMAIL    |USER_DOMAIN  | USER_ROLE | USER_NAME    | AVATAR       |
//VARCHAR(255)  |VARCHAR(255) |INT        | VARCHAR(255) | VARCHAR(255) | 
<PK>		 
 */
@Entity
@Table(name="USERS")
public class UserEntity  {
	
	private String userEmail;
	private String userDomain;
	private UserRole userRole;
	private String username;
	private String avatar;

	public UserEntity() {
	}

	@Id
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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
