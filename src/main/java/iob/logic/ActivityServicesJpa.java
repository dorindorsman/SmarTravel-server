package iob.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iob.data.ActivityCrud;
import iob.restAPI.ActivityBoundary;
import iob.utility.activity.ActivityConvertor;

@Service
public class ActivityServicesJpa implements ActivityServices{
	
	private ActivityCrud activityCrud;
	private ActivityConvertor activityConvertor;
	
	@Autowired
	public ActivityServicesJpa (ActivityCrud activityCrud, ActivityConvertor activityConvertor) {
		this.activityCrud = activityCrud;
		this.activityConvertor = activityConvertor;
	}

	@Override
	public Object invokeActivity(ActivityBoundary activityBoundary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllActivities() {
		
	}

}
