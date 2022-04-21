package iob.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.ActivityCrud;
import iob.data.ActivityEntity;
import iob.data.InstanceCrud;
import iob.data.InstanceEntity;
import iob.data.UserCrud;
import iob.data.UserEntity;
import iob.logic.exceptions.BadRequestException;
import iob.restAPI.ActivityBoundary;
import iob.utility.DomainWithEmail;
import iob.utility.DomainWithId;
import iob.utility.activity.ActivityConvertor;

@Service
public class ActivitiesServiceJpa implements ActivitiesService {

	private ActivityCrud activityCrud;
	private UserCrud userCrud;
	private InstanceCrud instanceCrud;
	private ActivityConvertor activityConvertor;
	private String configurableDomain;

	@Autowired
	public ActivitiesServiceJpa(InstanceCrud instanceCrud, UserCrud userCrud, ActivityCrud activityCrud,
			ActivityConvertor activityConvertor) {
		this.instanceCrud = instanceCrud;
		this.userCrud = userCrud;
		this.activityCrud = activityCrud;
		this.activityConvertor = activityConvertor;
	}

	@Value("${spring.application.name:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}

	@Override
	@Transactional(readOnly = false)
	public Object invokeActivity(ActivityBoundary activityBoundary) {

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

	@Transactional(readOnly = true)
	private boolean checkUserIdInDB(DomainWithEmail domainWithEmail) {
		String id = domainWithEmail.getDomain() + "_" + domainWithEmail.getEmail();

		Optional<UserEntity> op = userCrud.findById(id);
		if (op.isPresent())
			return true;
		return false;
	}
	
	@Transactional(readOnly = true)
	private boolean checkInstanceIdInDB(DomainWithId domainWithId) {
		String id = domainWithId.getDomain() + "_" + domainWithId.getId() ;
		
		Optional<InstanceEntity> op = instanceCrud.findById(id);
		if (op.isPresent())
			return true;
		return false;
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