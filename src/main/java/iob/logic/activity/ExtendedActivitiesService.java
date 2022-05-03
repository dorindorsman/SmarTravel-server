package iob.logic.activity;

import java.util.List;

import iob.restAPI.activity.ActivityBoundary;

public interface ExtendedActivitiesService extends ActivitiesService {
	public List<ActivityBoundary> getAllActivities(String userDomain, String userEmail, int size, int page);

	public void deleteAllActivities(String userDomain, String userEmail);
}
