package iob.utility.activity;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.ActivityEntity;
import iob.restAPI.ActivityBoundary;
import iob.utility.DomainWithEmail;
import iob.utility.DomainWithId;

@Component
public class ActivityConvertor {

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

	public ActivityEntity toEntity(ActivityBoundary boundary) {
		ActivityEntity entity = new ActivityEntity();

		entity.setActicityId(getId(boundary.getActicityId().getId(), boundary.getActicityId().getDomain()));
		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		entity.setInstance(getId(boundary.getInstance().getInstanceId().getId(),
				boundary.getInstance().getInstanceId().getDomain()));
		entity.setType(boundary.getType());
		entity.setActivityAttributes(toStringFromMap(boundary.getActivityAttributes()));
		
		return entity;
	}

	public ActivityBoundary toBoundary(ActivityEntity entity) {
		ActivityBoundary boundary = new ActivityBoundary();
		
		String[] splitedId = entity.getActicityId().split("_");
		boundary.setActicityId(new DomainWithId(splitedId[0], splitedId[1]));
		
		boundary.setActivityAttributes(toMapFromJsonString(entity.getActivityAttributes()));
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		String[] splitedInstanceId = entity.getInstance().split("_");
		boundary.setInstance(new Instance(new DomainWithId(splitedInstanceId[0], splitedInstanceId[1])));
		
		String[] splitedInvokedById = entity.getInstance().split("_");
		boundary.setInvokedBy(new InvokedBy(new DomainWithEmail(splitedInvokedById[0], splitedInvokedById[1])));
		
		boundary.setType(entity.getType());

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
