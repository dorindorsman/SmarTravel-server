package smarTravel.activities;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smarTravel.DomainWithEmail;
import smarTravel.DomainWithId;

@RestController
public class ActivityController {
	
	final String ACTIVITY_PATH = "/iob/admin/activities";
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = ACTIVITY_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] getAllActivities() {
		
		return Stream.of(new ActivityBoundary[] {
				new ActivityBoundary(new DomainWithId("domain", "1")),
				new ActivityBoundary(new DomainWithId("domain", "2")),
				new ActivityBoundary(new DomainWithId("domain", "3"))}
			).map(activityBoundary->{
				activityBoundary.setType("temp");
				activityBoundary.setCreatedTimestamp(new Date());
				activityBoundary.setInstance(new Instance(new DomainWithId("ins", "10")));
				activityBoundary.setInvokedBy(new InvokedBy(new DomainWithEmail("dom", "user")));
				activityBoundary.setActivityAttributes(new HashMap<String, Object>());
				return activityBoundary;
			})// Stream<ActivityBoundary>
			.collect(Collectors.toList())// List<ActivityBoundary>
			.toArray(new ActivityBoundary[0]);// ActivityBoundary[]
	}
}
