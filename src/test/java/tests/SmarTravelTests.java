package tests;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.Application;
import iob.restAPI.user.UserBoundary;
import iob.restAPI.user.NewUserBoundary;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SmarTravelTests {

	private int port;
	private RestTemplate restTemplate;
	private String baseUrl;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.baseUrl = "http://localhost:" + this.port;
		this.restTemplate = new RestTemplate();
	}

	@Test
	public void testPostUserOnServer() throws Exception {
		String postUrl = this.baseUrl + "/iob/users";
		String getUrl = this.baseUrl + "/iob/users/login/{userDomain}/{userEmail}";

		List<UserBoundary> all = new ArrayList<>();

		NewUserBoundary newUserBoundary = new NewUserBoundary();
		newUserBoundary.setEmail("user@gmail.com");
		newUserBoundary.setAvatar("avatar");
		newUserBoundary.setRole("ADMIN");
		newUserBoundary.setUsername("userName");
		UserBoundary rv = this.restTemplate.postForObject(postUrl, newUserBoundary,
				UserBoundary.class);
		all.add(rv);

		UserBoundary actual = this.restTemplate.getForObject(getUrl, UserBoundary.class, "2022b.maya.gembom",
				"user@gmail.com");

		assertThat(actual).isNotNull();
	}

}
