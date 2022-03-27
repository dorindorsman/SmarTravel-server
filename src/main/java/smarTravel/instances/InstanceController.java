package smarTravel.instances;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smarTravel.Domain;
import smarTravel.DomainWithEmail;
import smarTravel.DomainWithId;

@RestController
public class InstanceController {

	final String INSTANCE_PATH = "/iob/instances";
	final String INSTANCE_ADMIN_PATH = "/iob/admin/instances";

	@RequestMapping(
			method = RequestMethod.GET,
			path = INSTANCE_PATH + "/{instanceDomain}/{instanceId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(
			@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId) {
		
		InstanceBoundary instanceBoundary = new InstanceBoundary(
				new DomainWithId(instanceDomain, instanceId));
		instanceBoundary.setType("temp");
		instanceBoundary.setName("dammy name");
		instanceBoundary.setActive(true);
		instanceBoundary.setCreatedTimestamp(new Date());
		instanceBoundary.setCreatedBy(new CreatedBy(new DomainWithEmail("domain", "email")));
		instanceBoundary.setLocation(new Location(10.25616, 15.45866));
		instanceBoundary.setInstanceAttributes(new HashMap<String, Object>());

		return instanceBoundary;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = INSTANCE_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {
		
		return Stream.of(new InstanceBoundary[] {
				new InstanceBoundary(new DomainWithId("domain", "1")),
				new InstanceBoundary(new DomainWithId("domain", "2")),
				new InstanceBoundary(new DomainWithId("domain", "3"))}
			).map(instanceBoundary->{
				instanceBoundary.setType("temp");
				instanceBoundary.setName("dammy name");
				instanceBoundary.setActive(true);
				instanceBoundary.setCreatedTimestamp(new Date());
				instanceBoundary.setCreatedBy(new CreatedBy(new DomainWithEmail("domain", "email")));
				instanceBoundary.setLocation(new Location(10.25616, 15.45866));
				instanceBoundary.setInstanceAttributes(new HashMap<String, Object>());
				return instanceBoundary;
			})// Stream<InstanceBoundary>
			.collect(Collectors.toList())// List<InstanceBoundary>
			.toArray(new InstanceBoundary[0]);// InstanceBoundary[]
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = INSTANCE_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary createInstance (@RequestBody InstanceBoundary boundary) {
			
			boundary.setInstanceId(new DomainWithId(Domain.DOMAIN, UUID.randomUUID().toString()));
			
			return boundary;
		}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = INSTANCE_PATH + "/{instanceDomain}/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void UpdateInstace (
				@PathVariable("instanceDomain") String instanceDomain, 
				@PathVariable("instanceId") String instanceId, 
				@RequestBody InstanceBoundary updateBoundary) {
			// TODO update db
		}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = INSTANCE_ADMIN_PATH)
		public void deleteAllInstances () {
			// delete instances from db here
		}
}
