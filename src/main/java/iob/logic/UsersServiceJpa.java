package iob.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.UserEntity;
import iob.logic.exceptions.BadRequestException;
import iob.logic.exceptions.ObjNotFoundException;
import iob.restAPI.UserBoundary;
import iob.utility.user.UserConverter;
import iob.data.UserCrud;

@Service
public class UsersServiceJpa implements UsersService {

	private UserCrud userCrud;
	private UserConverter userConverter;
	private String configurableDomain;
	
	@Value("${spring.application.name:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}

	@Autowired
	public UsersServiceJpa(UserCrud userCrud, UserConverter userConverter) {
		this.userCrud = userCrud;
		this.userConverter = userConverter;
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary createUser(UserBoundary userToStore) {
		userToStore.getUserId().setDomain(configurableDomain);
		UserEntity entity = this.userConverter.toEntity(userToStore);
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		return this.userConverter.toBoundary(getUserEntityById(userDomain, userEmail));
	}
	
	@Transactional(readOnly = true)
	private UserEntity getUserEntityById(String userDomain, String userEmail) {
		Optional<UserEntity> optionalUser = this.userCrud.findById(userDomain + "_" + userEmail);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new ObjNotFoundException(
					"Could not find user by mail: " + userEmail + " and by domain: " + userDomain);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		if (userDomain == null || userDomain.isEmpty())
			throw new BadRequestException();
		if (userEmail == null || userEmail.isEmpty())
			throw new BadRequestException();
		if (update == null)
			throw new BadRequestException();
		
		UserEntity userEntity = getUserEntityById(userDomain, userEmail);
		userEntity = userConverter.updateEntityByBoundary(userEntity, update);
		
		userCrud.save(userEntity);
		
		return userConverter.toBoundary(userEntity);
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
