package smarTravel.users;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.UserServices;
import smarTravel.Domain;
import smarTravel.DomainWithEmail;

@RestController
public class UserController {
	
	private UserServices userServices;
	private String configurableDomain;
	
	@Value("${configurable.domain.text:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}
	
	@Autowired
	public UserController(UserServices userServices) {
		this.userServices = userServices;
	}

	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/users/login/{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginUser(
			@PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		
		return this.userServices.login(userDomain, userEmail);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/admin/users",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getAllUsers() {
		
		return this.userServices.getAllUsers().toArray(new UserBoundary[0]);
		
//		
//		return Stream.of(new UserBoundary[] {
//				new UserBoundary(new DomainWithEmail("domain", "email1")),
//				new UserBoundary(new DomainWithEmail("domain", "email2")),
//				new UserBoundary(new DomainWithEmail("domain", "email3"))}
//			).map(userBoundary->{
//				userBoundary.setRole("test");
//				userBoundary.setUsername("dammy name");
//				userBoundary.setAvatar("uri avatar");
//				return userBoundary;
//			})// Stream<UserBoundary>
//			.collect(Collectors.toList())// List<UserBoundary>
//			.toArray(new UserBoundary[0]);// UserBoundary[]
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/users",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary createUser (@RequestBody NewUserBoundary boundary) {
		
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setUserId(new DomainWithEmail(configurableDomain, boundary.getEmail()));
		userBoundary.setAvatar(boundary.getAvatar());
		userBoundary.setRole(boundary.getRole());
		userBoundary.setUsername(boundary.getUsername());
		
		return this.userServices.createUser(userBoundary);
		}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = "/iob/users/{userDomain}/{userEmail}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void UpdateUser (
				@PathVariable("userDomain") String userDomain, 
				@PathVariable("userEmail") String userEmail, 
				@RequestBody UserBoundary updateBoundary) {
		
			this.userServices.updateUser(userDomain, userEmail, updateBoundary);
			
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/users")
		public void deleteAllUsers () {
		
			this.userServices.deleteAllUsers();
			
		}
}
