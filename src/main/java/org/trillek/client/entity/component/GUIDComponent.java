package org.trillek.client.entity.component;

public class GUIDComponent implements Component {
	
	private final String guid;
	
	public GUIDComponent(final String guid) {
		this.guid = guid;
	}
	
	public String getGUID() {
		return this.guid;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GUID;
	}

	@Override
	public void cleanup() { }

}
