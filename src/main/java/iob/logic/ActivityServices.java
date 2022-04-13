package iob.logic;

import java.util.List;

import iob.restAPI.ActivityBoundary;

public interface ActivityServices {
	public Object invokeActivity(ActivityBoundary activityBoundary);
	public List<ActivityBoundary> getAllActivities();
	public void deleteAllActivities();
}
