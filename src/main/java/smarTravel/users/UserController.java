package smarTravel.users;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	final String GET_USER_PATH = "/iob/users/login/";
	final String PUT_USER_PATH = "/iob/users/";
	final String POST_USER_PATH = "/iob/users";

	@RequestMapping(
			method = RequestMethod.GET,
			path = GET_USER_PATH + "{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginUser(
			@PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		
		UserBoundary userBoundary = new UserBoundary(new UserId(userDomain, userEmail));
		userBoundary.setRole("test");
		userBoundary.setUsername("dammy name");
		userBoundary.setAvatar("uri");

		return userBoundary;
	}
}
