package smarTravel.activities;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smarTravel.Domain;
import smarTravel.DomainWithEmail;
import smarTravel.DomainWithId;

@RestController
public class ActivityController {
	
	final String ACTIVITY_ADMIN_PATH = "/iob/admin/activities";
	final String ACTIVITY_PATH = "/iob/activities";
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = ACTIVITY_ADMIN_PATH,
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
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = ACTIVITY_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public Object createActivity (@RequestBody ActivityBoundary boundary) {
	
			boundary.setActicityId(new DomainWithId(Domain.DOMAIN, UUID.randomUUID().toString()));
			
			return boundary;
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = ACTIVITY_ADMIN_PATH)
		public void deleteAllUsers () {
			// delete activities from db here
		}
}
