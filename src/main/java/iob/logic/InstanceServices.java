package iob.logic;

import java.util.List;

import iob.restAPI.InstanceBoundary;

public interface InstanceServices {
	public InstanceBoundary createInstance(InstanceBoundary instance);
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update);
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId);
	public List<InstanceBoundary> getAllInstances();
	public void deleteAllInstances();
}
