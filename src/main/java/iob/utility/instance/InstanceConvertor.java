package iob.utility.instance;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.InstanceEntity;
import iob.restAPI.InstanceBoundary;

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
	
	public InstanceEntity toEntity(InstanceBoundary boundary) {
		InstanceEntity entity = new InstanceEntity();
		
		// todo
		
		return entity;
	}

	public InstanceBoundary toBoundary(InstanceEntity entity) {
		InstanceBoundary boundary = new InstanceBoundary();

		//todo

		return boundary;
	}
	
	private String toStringFromMap (Map<String, Object> object) {
		try {
			return this.jackson
				.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Map<String, Object> toMapFromJsonString (String json){
		try {
			return this.jackson
				.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
