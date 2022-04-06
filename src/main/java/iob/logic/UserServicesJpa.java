package iob.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.UserEntity;
import iob.data.UserRole;
import iob.data.UserCrud;
import smarTravel.DomainWithEmail;
import smarTravel.users.UserBoundary;

@Service
public class UserServicesJpa implements UserServices {

	private String configurableDomain;
	private UserCrud userCrud;

	@Value("${configurable.domain.text:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}
	
	@Autowired
	public UserServicesJpa (UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary createUser(UserBoundary userToStore) {
		UserEntity entity = toEntity(userToStore);
		entity = this.userCrud.save(entity);
		return this.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		Optional<UserEntity> optionalUser = this.userCrud.findById(userEmail);
		UserEntity entity = new UserEntity();
		if (optionalUser.isPresent()) {
			entity = optionalUser.get();
		}
		return toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		Optional<UserEntity> optionalUser = this.userCrud.findById(userEmail);
		if (optionalUser.isPresent()) {
			UserEntity entity = toEntity(update);
			entity = this.userCrud.save(entity);
			update = toBoundary(entity);
		}
		return update;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers() {

		Iterable<UserEntity> iter = this.userCrud.findAll();

		List<UserBoundary> rv = new ArrayList<>();
		for (UserEntity user : iter) {
			rv.add(this.toBoundary(user));
		}

		return rv;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllUsers() {
		userCrud.deleteAll();
	}

	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();

		entity.setUserEmail(boundary.getUserId().getEmail());
		entity.setUserDomain(configurableDomain);
		entity.setAvatar(boundary.getAvatar());
		try {
			entity.setUserRole(UserRole.valueOf(boundary.getRole()));
		} catch (Exception e) {

		}
		entity.setUsername(boundary.getUsername());

		return entity;
	}

	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();

		boundary.setUserId(new DomainWithEmail(entity.getUserEmail(), entity.getUserDomain()));
		boundary.setAvatar(entity.getAvatar());
		boundary.setRole(entity.getUserRole().toString());
		boundary.setUsername(entity.getUsername());

		return boundary;
	}

}
