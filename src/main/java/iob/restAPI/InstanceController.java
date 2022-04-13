package iob.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.InstanceServices;

@RestController
public class InstanceController {
	
	private InstanceServices instanceServices;
	
	@Autowired
	public InstanceController(InstanceServices instanceServices) {
		this.instanceServices = instanceServices;
	}

	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(
			@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId) {

		return this.instanceServices.getSpecificInstance(instanceDomain, instanceId);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {
		
		return this.instanceServices.getAllInstances().toArray(new InstanceBoundary[0]);
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/instances",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary createInstance (@RequestBody InstanceBoundary boundary) {
			
			return this.instanceServices.createInstance(boundary);
		}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void UpdateInstace (
				@PathVariable("instanceDomain") String instanceDomain, 
				@PathVariable("instanceId") String instanceId, 
				@RequestBody InstanceBoundary updateBoundary) {
			
			this.instanceServices.updateInstance(instanceDomain, instanceId, updateBoundary);
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/instances")
		public void deleteAllInstances () {
			
			this.instanceServices.deleteAllInstances();
		}
}
