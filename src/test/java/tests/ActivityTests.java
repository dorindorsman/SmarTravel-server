package tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import iob.restAPI.activity.ActivityBoundary;
import iob.restAPI.instance.InstanceBoundary;
import iob.restAPI.user.NewUserBoundary;
import iob.restAPI.user.UserBoundary;
import iob.utility.DomainWithEmail;

import iob.utility.activity.Instance;
import iob.utility.activity.InvokedBy;


public class ActivityTests extends Base{


	@Test
	public void testPostActivityOnServer() throws Exception {
		String postUrlActivityBoundary = this.baseUrl + "/iob/activities";
		String postUrlUsers = this.baseUrl + "/iob/users";
		String postInstanceBoundary = this.baseUrl + "/iob/instances";
		String getUrl = this.baseUrl + "/iob/admin/activities?userDomain={userDomain}&userEmail={userEmail}&size={size}&page={page}";
		
		NewUserBoundary newUserBoundary = Helper.getNewUserBoundary("ADMIN");
			UserBoundary userBoundary = this.restTemplate.postForObject(postUrlUsers,
				newUserBoundary, UserBoundary.class);
	
		DomainWithEmail de = new DomainWithEmail(userBoundary.getUserId().getDomain(), userBoundary.getUserId().getEmail());
		InvokedBy inv = new InvokedBy(de);

		InstanceBoundary instanceBoundary = this.restTemplate.postForObject(
				postInstanceBoundary, Helper.getInstanceBoundary("testType", "testName",
						userBoundary.getUserId().getDomain(), userBoundary.getUserId().getEmail()),
				InstanceBoundary.class);	
		
		ActivityBoundary activityBoundary = new ActivityBoundary();
		activityBoundary.setInvokedBy(inv);
		activityBoundary.setType("test");
		activityBoundary.setInstance(new Instance(instanceBoundary.getInstanceId()));
		activityBoundary.setActivityAttributes(new HashMap<String, Object>());

		this.restTemplate.postForObject(postUrlActivityBoundary, activityBoundary,
				ActivityBoundary.class);

		ActivityBoundary[] actual = this.restTemplate.getForObject(getUrl, ActivityBoundary[].class, "2022b.maya.gembom",
				userBoundary.getUserId().getEmail(),10,0);

		assertThat(actual).isNotNull().hasSize(1);
	}
	
	
	@Test
	public void testDeleteActivityOnServer() throws Exception {
		String postUrlActivityBoundary = this.baseUrl + "/iob/activities";
		String postUrlUsers = this.baseUrl + "/iob/users";
		String postInstanceBoundary = this.baseUrl + "/iob/instances";
		String getUrl = this.baseUrl + "/iob/admin/activities?userDomain={userDomain}&userEmail={userEmail}&size={size}&page={page}";
		String delUrl = this.baseUrl + "/iob/admin/activities?userDomain={userDomain}&userEmail={userEmail}";

		NewUserBoundary newUserBoundary = Helper.getNewUserBoundary("ADMIN");
		UserBoundary userBoundary = this.restTemplate.postForObject(postUrlUsers,
				newUserBoundary, UserBoundary.class);
	
		DomainWithEmail de = new DomainWithEmail(userBoundary.getUserId().getDomain(), userBoundary.getUserId().getEmail());
		InvokedBy inv = new InvokedBy(de);

		InstanceBoundary instanceBoundary = this.restTemplate.postForObject(
				postInstanceBoundary, Helper.getInstanceBoundary("testType", "testName",
						userBoundary.getUserId().getDomain(), userBoundary.getUserId().getEmail()),
				InstanceBoundary.class);	
		
		ActivityBoundary activityBoundary = new ActivityBoundary();
		activityBoundary.setInvokedBy(inv);
		activityBoundary.setType("test");
		activityBoundary.setInstance(new Instance(instanceBoundary.getInstanceId()));
		activityBoundary.setActivityAttributes(new HashMap<String, Object>());

		this.restTemplate.postForObject(postUrlActivityBoundary, activityBoundary,
				ActivityBoundary.class);

		
		this.restTemplate.delete(delUrl, "2022b.maya.gembom",
				userBoundary.getUserId().getEmail());
		
		ActivityBoundary[] actual = this.restTemplate.getForObject(getUrl, ActivityBoundary[].class, "2022b.maya.gembom",
				userBoundary.getUserId().getEmail(),10,0);

		assertThat(actual).isEmpty();
		
	}
	
}
