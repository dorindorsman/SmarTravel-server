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

import iob.data.InstanceCrud;
import iob.data.InstanceEntity;
import iob.data.UserCrud;
import iob.data.UserEntity;
import iob.logic.exceptions.BadRequestException;
import iob.logic.exceptions.ObjNotFoundException;
import iob.restAPI.InstanceBoundary;
import iob.utility.DomainWithEmail;
import iob.utility.DomainWithId;
import iob.utility.instance.InstanceConvertor;

@Service
public class InstancesServiceJpa implements InstancesService {

	private InstanceCrud instanceCrud;
	private UserCrud userCrud;
	private InstanceConvertor instanceConvertor;
	private String configurableDomain;

	@Autowired
	public InstancesServiceJpa(UserCrud userCrud, InstanceCrud instanceCrud, InstanceConvertor instanceConvertor) {
		this.userCrud = userCrud;
		this.instanceCrud = instanceCrud;
		this.instanceConvertor = instanceConvertor;
	}

	@Value("${spring.application.name:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}

	@Override
	@Transactional(readOnly = false)
	public InstanceBoundary createInstance(InstanceBoundary instanceBoundary) {

		instanceBoundary.setInstanceId(new DomainWithId(configurableDomain, UUID.randomUUID().toString()));
		if (instanceBoundary.getCreatedTimestamp() == null
				|| instanceBoundary.getCreatedTimestamp().toString().isEmpty())
			instanceBoundary.setCreatedTimestamp(new Date());
		
		if (!checkUserIdInDB(instanceBoundary.getCreatedBy().getUserId()))
			throw new BadRequestException();
		
		InstanceEntity instanceEntity = instanceConvertor.toEntity(instanceBoundary);
		instanceCrud.save(instanceEntity);
		return instanceConvertor.toBoundary(instanceEntity);
	}
	
	@Transactional(readOnly = true)
	private boolean checkUserIdInDB(DomainWithEmail domainWithEmail) {
		String id = domainWithEmail.getDomain() + "_" + domainWithEmail.getEmail();

		Optional<UserEntity> op = userCrud.findById(id);
		if (op.isPresent())
			return true;
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		if (instanceDomain == null || instanceDomain.isEmpty())
				throw new BadRequestException();
		if (instanceId == null || instanceId.isEmpty())
			throw new BadRequestException();
		if (update == null)
			throw new BadRequestException();
		
		InstanceEntity instanceEntity = getInstanceEntityById(instanceDomain, instanceId);
		instanceEntity = instanceConvertor.updateEntityByBoundary(instanceEntity, update);

		instanceCrud.save(instanceEntity);

		return instanceConvertor.toBoundary(instanceEntity);
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		return instanceConvertor.toBoundary(getInstanceEntityById(instanceDomain, instanceId));
	}

	@Transactional(readOnly = true)
	private InstanceEntity getInstanceEntityById(String instanceDomain, String instanceId) {
		String id = instanceDomain + "_" + instanceId;
		Optional<InstanceEntity> op = instanceCrud.findById(id);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ObjNotFoundException(
					"Could not find instance by id: " + instanceId + " and by domain: " + instanceDomain);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllInstances() {
		Iterable<InstanceEntity> iter = instanceCrud.findAll();

		List<InstanceBoundary> rv = new ArrayList<>();
		for (InstanceEntity inEntity : iter) {
			rv.add(this.instanceConvertor.toBoundary(inEntity));
		}

		return rv;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllInstances() {
		instanceCrud.deleteAll();
	}

}
