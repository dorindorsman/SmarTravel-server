package iob.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.UserEntity;
import iob.restAPI.UserBoundary;
import iob.utility.user.UserConverter;
import iob.data.UserCrud;

@Service
public class UserServicesJpa implements UserServices {
	
	private UserCrud userCrud;
	private UserConverter userConverter;
	
	@Autowired
	public UserServicesJpa (UserCrud userCrud, UserConverter userConverter) {
		this.userCrud = userCrud;
		this.userConverter = userConverter;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary createUser(UserBoundary userToStore) {
		UserEntity entity = this.userConverter.toEntity(userToStore);
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		Optional<UserEntity> optionalUser = this.userCrud.findById(userEmail);
		UserEntity entity = new UserEntity();
		if (optionalUser.isPresent()) {
			entity = optionalUser.get();
		}
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		Optional<UserEntity> optionalUser = this.userCrud.findById(userEmail + "_" + userDomain);
		if (optionalUser.isPresent()) {
			UserEntity entity = this.userConverter.toEntity(update);
			entity = this.userCrud.save(entity);
			update = this.userConverter.toBoundary(entity);
			return update;
		}else {
			throw new UserNotFoundException("Could not find user by mail: " + userEmail + " and by domain: " + userDomain);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers() {

		Iterable<UserEntity> iter = this.userCrud.findAll();

		List<UserBoundary> rv = new ArrayList<>();
		for (UserEntity user : iter) {
			rv.add(this.userConverter.toBoundary(user));
		}

		return rv;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllUsers() {
		userCrud.deleteAll();
	}

}
