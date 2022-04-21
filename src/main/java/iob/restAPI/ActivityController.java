package iob.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.ActivitiesService;

@RestController
public class ActivityController {
	
	private ActivitiesService activityServices;
	
	@Autowired
	public ActivityController(ActivitiesService activityServices) {
		this.activityServices = activityServices;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/admin/activities",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] getAllActivities() {
		
		return this.activityServices.getAllActivities().toArray(new ActivityBoundary[0]);
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/activities",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public Object createActivity (@RequestBody ActivityBoundary boundary) {
	
			return this.activityServices.invokeActivity(boundary);
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/activities")
		public void deleteAllUsers () {

			this.activityServices.deleteAllActivities();
		}
}
