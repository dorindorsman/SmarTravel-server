package tests;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import iob.restAPI.instance.InstanceBoundary;
import iob.restAPI.user.NewUserBoundary;
import iob.utility.DomainWithEmail;
import iob.utility.DomainWithId;
import iob.utility.instance.CreatedBy;
import iob.utility.instance.Location;

public abstract class Helper {

	public static NewUserBoundary getNewUserBoundary(String role) {
		NewUserBoundary newUserBoundary = new NewUserBoundary();
		newUserBoundary.setEmail(role + "User@gmail.com");
		newUserBoundary.setAvatar("avatar");
		newUserBoundary.setRole(role);
		newUserBoundary.setUsername("userName");
		return newUserBoundary;
	}
	
	public static InstanceBoundary getInstanceBoundary(String type, String name, String domain, String email) {
		InstanceBoundary instanceBoundary = new InstanceBoundary();
		
		instanceBoundary.setType(type);
		instanceBoundary.setName(name);
		instanceBoundary.setCreatedBy(new CreatedBy(new DomainWithEmail(domain, email)));
		
		//instanceBoundary.setCreatedTimestamp(new Date());
		//instanceBoundary.setInstanceId(new DomainWithId(domain, UUID.randomUUID().toString()));
		
		Random rd = new Random();
		
		instanceBoundary.setActive(rd.nextBoolean());
		instanceBoundary.setLocation(new Location(rd.nextDouble() * 10, rd.nextDouble() * 10));
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", rd.nextBoolean());
		map.put("key2", rd.nextDouble());
		map.put("key3", rd.nextInt());
		instanceBoundary.setInstanceAttributes(map);
		
		return instanceBoundary;
	}
	
	public static void updateInstanceBoundary (InstanceBoundary old) {
		Random rd = new Random();
		old.setActive(rd.nextBoolean());
		old.setLocation(new Location(rd.nextDouble() * 10, rd.nextDouble() * 10));
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", rd.nextBoolean());
		map.put("key2", rd.nextDouble());
		map.put("key3", rd.nextInt());
		old.setInstanceAttributes(map);
	}
}
