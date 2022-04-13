package iob.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iob.data.InstanceCrud;
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
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		return null;
	}

	@Override
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
