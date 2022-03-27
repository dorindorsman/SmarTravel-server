package smarTravel.users;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smarTravel.Domain;
import smarTravel.DomainWithEmail;

@RestController
public class UserController {

	final String GET_USER_PATH = "/iob/users/login/";
	final String PUT_USER_PATH = "/iob/users/";
	final String POST_USER_PATH = "/iob/users";
	final String USERS_ADMIN_PATH = "/iob/admin/users";

	@RequestMapping(
			method = RequestMethod.GET,
			path = GET_USER_PATH + "{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginUser(
			@PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		
		UserBoundary userBoundary = new UserBoundary(new DomainWithEmail(userDomain, userEmail));
		userBoundary.setRole("test");
		userBoundary.setUsername("dammy name");
		userBoundary.setAvatar("uri");

		return userBoundary;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = USERS_ADMIN_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getAllUsers() {
		
		return Stream.of(new UserBoundary[] {
				new UserBoundary(new DomainWithEmail("domain", "email1")),
				new UserBoundary(new DomainWithEmail("domain", "email2")),
				new UserBoundary(new DomainWithEmail("domain", "email3"))}
			).map(userBoundary->{
				userBoundary.setRole("test");
				userBoundary.setUsername("dammy name");
				userBoundary.setAvatar("uri avatar");
				return userBoundary;
			})// Stream<UserBoundary>
			.collect(Collectors.toList())// List<UserBoundary>
			.toArray(new UserBoundary[0]);// UserBoundary[]
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = POST_USER_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary createUser (@RequestBody NewUserBoundary boundary) {
			UserBoundary userBoundary = new UserBoundary();
			userBoundary.setUserId(new DomainWithEmail(Domain.DOMAIN, boundary.getEmail()));
			userBoundary.setAvatar(boundary.getAvatar());
			userBoundary.setRole(boundary.getRole());
			userBoundary.setUsername(boundary.getUsername());
			return userBoundary;
		}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = PUT_USER_PATH + "{userDomain}/{userEmail}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void UpdateUser (
				@PathVariable("userDomain") String userDomain, 
				@PathVariable("userEmail") String userEmail, 
				@RequestBody UserBoundary updateBoundary) {
			// TODO update db
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = USERS_ADMIN_PATH)
		public void deleteAllUsers () {
			// TODO delete users from db here
		}
}
