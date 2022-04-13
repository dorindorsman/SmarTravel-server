package iob.utility.instance;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.InstanceEntity;
import iob.restAPI.InstanceBoundary;
import iob.utility.DomainWithEmail;
import iob.utility.DomainWithId;

@Component
public class InstanceConvertor {

	private String configurableDomain;
	private ObjectMapper jackson;

	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}

	@Value("${configurable.domain.text:2022b}")
	public void setConfigurableDomain(String configurableDomain) {
		this.configurableDomain = configurableDomain;
	}

	private String getId(String id, String domain) {
		if (id == null || domain == null)
			return null;
		return id + "_" + domain;
	}

	public InstanceEntity toEntity(InstanceBoundary boundary) {
		InstanceEntity entity = new InstanceEntity();

		entity.setActive(boundary.isActive());

		entity.setCreatedBy(
				getId(boundary.getCreatedBy().getUserId().getEmail(), boundary.getCreatedBy().getUserId().getDomain()));

		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		
		entity.setInstanceAttributes(toStringFromMap(boundary.getInstanceAttributes()));
		
		entity.setLocationLat(boundary.getLocation().getLat());
		
		entity.setLocationLng(boundary.getLocation().getLng());
		
		entity.setName(boundary.getName());
		
		entity.setType(boundary.getType());
		
		entity.setInstanceId(getId(boundary.getInstanceId().getId(), boundary.getInstanceId().getDomain()));
		
		return entity;
	}

	public InstanceBoundary toBoundary(InstanceEntity entity) {
		InstanceBoundary boundary = new InstanceBoundary();

		boundary.setActive(entity.isActive());

		String[] splitedId = entity.getCreatedBy().split("_");
		boundary.setCreatedBy(new CreatedBy(new DomainWithEmail(splitedId[0], splitedId[1])));

		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());

		boundary.setLocation(new Location(entity.getLocationLat(), entity.getLocationLng()));

		boundary.setName(entity.getName());

		boundary.setType(entity.getType());

		boundary.setInstanceAttributes(toMapFromJsonString(entity.getInstanceAttributes()));

		String[] splitedInstanceId = entity.getCreatedBy().split("_");
		boundary.setInstanceId(new DomainWithId(splitedInstanceId[0], splitedInstanceId[1]));

		return boundary;
	}

	private String toStringFromMap(Map<String, Object> object) {
		try {
			return this.jackson.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> toMapFromJsonString(String json) {
		try {
			return this.jackson.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
