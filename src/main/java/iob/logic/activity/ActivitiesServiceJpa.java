package iob.logic.activity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.activity.ActivityCrud;
import iob.data.activity.ActivityEntity;
import iob.data.instance.InstanceCrud;
import iob.data.user.UserCrud;
import iob.data.user.UserRole;
import iob.logic.ServiceJpa;
import iob.logic.exceptions.BadRequestException;
import iob.logic.exceptions.DeprecatedMethodException;
import iob.restAPI.activity.ActivityBoundary;
import iob.utility.DomainWithId;
import iob.utility.activity.ActivityConvertor;

@Service
public class ActivitiesServiceJpa extends ServiceJpa implements ExtendedActivitiesService {

	private ActivityConvertor activityConvertor;

	@Autowired
	public ActivitiesServiceJpa(InstanceCrud instanceCrud, UserCrud userCrud, ActivityCrud activityCrud,
			ActivityConvertor activityConvertor) {
		super(userCrud, activityCrud, instanceCrud);
		this.activityConvertor = activityConvertor;
	}

	@Override
	@Transactional(readOnly = false)
	public Object invokeActivity(ActivityBoundary activityBoundary) {
		
		// player only - active only

		activityBoundary.setActicityId(new DomainWithId(configurableDomain, UUID.randomUUID().toString()));
		if (activityBoundary.getCreatedTimestamp() == null
				|| activityBoundary.getCreatedTimestamp().toString().isEmpty())
			activityBoundary.setCreatedTimestamp(new Date());

		if (!checkUserIdInDB(activityBoundary.getInvokedBy().getUserId()))
			throw new BadRequestException();
		
		if (!checkInstanceIdInDB(activityBoundary.getInstance().getInstanceId()))
			throw new BadRequestException();

		ActivityEntity activityEntity = activityConvertor.toEntity(activityBoundary);
		activityCrud.save(activityEntity);

		return activityConvertor.toBoundary(activityEntity); // temp - json
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		throw new DeprecatedMethodException(
				"deprecated method - use getAllActivities with with paginayion instead");
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities(String userDomain, String userEmail, int size, int page) {
		UserRole userRole = checkUserRoleInDB(userDomain, userEmail);
		if (userRole == null)
			throw new BadRequestException();
		
		if (userRole != UserRole.ADMIN)
			throw new BadRequestException();
		
		List<ActivityBoundary> allActivityBoundaries = this.activityCrud
				.findAll(PageRequest.of(page, size, Direction.ASC, "createdTimestamp", "acticityId"))
				.stream()
				.map(this.activityConvertor::toBoundary).collect(Collectors.toList());

		return allActivityBoundaries;
	}

	@Override
	public void deleteAllActivities() {
		throw new DeprecatedMethodException(
				"deprecated method - use deleteAllActivities with user domin and user email");
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteAllActivities(String userDomain, String userEmail) {
		UserRole userRole = checkUserRoleInDB(userDomain, userEmail);
		if (userRole == null)
			throw new BadRequestException();
		if (userRole == UserRole.ADMIN) {
			activityCrud.deleteAll();
			return;
		}
		throw new BadRequestException();
	}
}
