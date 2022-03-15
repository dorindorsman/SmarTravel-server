package smarTravel.instances;

import java.util.Map;

public class InstanceAttributes {
	private Map<String, Object> flexibleField;
	
	public InstanceAttributes() {}

	public Map<String, Object> getFlexibleField() {
		return flexibleField;
	}

	public void setFlexibleField(Map<String, Object> flexibleField) {
		this.flexibleField = flexibleField;
	}

	@Override
	public String toString() {
		return "InstanceAttributes [flexibleField=" + flexibleField + "]";
	};
}
