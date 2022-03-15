package smarTravel.instances;

import java.util.Date;

public class InstanceBoundary {
	private InstanceId instanceId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private InstanceAttributes instanceAttributes;
	
	public InstanceBoundary() {
		
	}
	
	public InstanceBoundary(InstanceId instanceId) {
		this.instanceId = instanceId;
	}

	public InstanceId getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(InstanceId instanceId) {
		this.instanceId = instanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public InstanceAttributes getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(InstanceAttributes instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
	}

	@Override
	public String toString() {
		return "InstanceBoundary [instanceId=" + instanceId + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimestamp=" + createdTimestamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", instanceAttributes=" + instanceAttributes + "]";
	}
	
}
