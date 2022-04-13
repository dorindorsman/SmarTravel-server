package iob.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.ActivityCrud;
import iob.data.ActivityEntity;
import iob.data.UserEntity;
import iob.restAPI.ActivityBoundary;
import iob.restAPI.UserBoundary;
import iob.utility.activity.ActivityConvertor;

@Service
public class ActivityServicesJpa implements ActivityServices {

	private ActivityCrud activityCrud;
	private ActivityConvertor activityConvertor;

	@Autowired
	public ActivityServicesJpa(ActivityCrud activityCrud, ActivityConvertor activityConvertor) {
		this.activityCrud = activityCrud;
		this.activityConvertor = activityConvertor;
	}

	@Override
	@Transactional(readOnly = false)
	public Object invokeActivity(ActivityBoundary activityBoundary) {
		ActivityEntity activityEntity = activityConvertor.toEntity(activityBoundary);
		activityCrud.save(activityEntity);
		return null; // ???
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities() {
		Iterable<ActivityEntity> iter = this.activityCrud.findAll();

		List<ActivityBoundary> rv = new ArrayList<>();
		for (ActivityEntity acEntity : iter) {
			rv.add(this.activityConvertor.toBoundary(acEntity));
		}

		return rv;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllActivities() {
		activityCrud.deleteAll();
	}

}
