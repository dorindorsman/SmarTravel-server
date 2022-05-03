package iob.logic.activity;

import java.util.List;

import iob.restAPI.activity.ActivityBoundary;

public interface ActivitiesService {
	public Object invokeActivity(ActivityBoundary activityBoundary);

	@Deprecated
	public List<ActivityBoundary> getAllActivities();

	@Deprecated
	public void deleteAllActivities();
}
