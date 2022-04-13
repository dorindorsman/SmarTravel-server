package iob.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.InstanceCrud;
import iob.data.InstanceEntity;
import iob.restAPI.InstanceBoundary;
import iob.utility.instance.InstanceConvertor;

@Service
public class InstanceServicesJpa implements InstanceServices{
	
	private InstanceCrud instanceCrud;
	private InstanceConvertor instanceConvertor;
	
	@Autowired
	public InstanceServicesJpa (InstanceCrud instanceCrud, InstanceConvertor instanceConvertor) {
		this.instanceCrud = instanceCrud;
		this.instanceConvertor = instanceConvertor;
	}

	@Override
	@Transactional(readOnly = false)
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		InstanceEntity instanceEntity = instanceConvertor.toEntity(instance);
		instanceCrud.save(instanceEntity);
		return instanceConvertor.toBoundary(instanceEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		return null;
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		return null;
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		return null;
	}

	@Override
	public void deleteAllInstances() {
		
	}

}
