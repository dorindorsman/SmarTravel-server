package smarTravel.instances;


import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smarTravel.users.UserId;

@RestController
public class InstanceController {

	final String INSTACE_PATH = "/iob/instances";

	@RequestMapping(
			method = RequestMethod.GET,
			path = INSTACE_PATH + "{instanceDomain}/{instanceId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(
			@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId) {
		
		InstanceBoundary instanceBoundary = new InstanceBoundary(
				new InstanceId(instanceDomain, instanceId));
		instanceBoundary.setType("temp");
		instanceBoundary.setName("dammy name");
		instanceBoundary.setActive(true);
		instanceBoundary.setCreatedTimestamp(new Date());
		instanceBoundary.setCreatedBy(new CreatedBy(new UserId("domain", "email")));
		instanceBoundary.setLocation(new Location(10.25616, 15.45866));
		instanceBoundary.setInstanceAttributes(new InstanceAttributes());

		return instanceBoundary;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = INSTACE_PATH,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {
		
		return Stream.of(new InstanceBoundary[] {
				new InstanceBoundary(new InstanceId("domain", "1")),
				new InstanceBoundary(new InstanceId("domain", "2")),
				new InstanceBoundary(new InstanceId("domain", "3"))}
			).map(instanceBoundary->{
				instanceBoundary.setType("temp");
				instanceBoundary.setName("dammy name");
				instanceBoundary.setActive(true);
				instanceBoundary.setCreatedTimestamp(new Date());
				instanceBoundary.setCreatedBy(new CreatedBy(new UserId("domain", "email")));
				instanceBoundary.setLocation(new Location(10.25616, 15.45866));
				instanceBoundary.setInstanceAttributes(new InstanceAttributes());
				return instanceBoundary;
			})// Stream<InstanceBoundary>
			.collect(Collectors.toList())// List<InstanceBoundary>
			.toArray(new InstanceBoundary[0]);// InstanceBoundary[]
	}
}
