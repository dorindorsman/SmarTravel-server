package iob.utility.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import iob.data.UserEntity;
import iob.data.UserRole;
import iob.restAPI.UserBoundary;
import iob.utility.DomainWithEmail;

@Component
public class UserConverter {
	
	private String configurableDomain;
	
	@Value("${configurable.domain.text:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}
	
	private String getUserId(String email, String domain) {
		if (email == null || domain == null)
			return null;
		return email + "_" + domain;
	}

	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();

		entity.setUserId(getUserId(boundary.getUserId().getEmail(), configurableDomain));
		entity.setAvatar(boundary.getAvatar());
		
		try {
			entity.setUserRole(UserRole.valueOf(boundary.getRole()));
		} catch (Exception e) {
			entity.setUserRole(UserRole.PLAYER); // Default value
		}
		
		entity.setUsername(boundary.getUsername());

		return entity;
	}

	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();

		String[] splitedId = entity.getUserId().split("_");
		boundary.setUserId(new DomainWithEmail(splitedId[0], splitedId[1]));
		boundary.setAvatar(entity.getAvatar());
		boundary.setRole(entity.getUserRole().toString());
		boundary.setUsername(entity.getUsername());

		return boundary;
	}
}
