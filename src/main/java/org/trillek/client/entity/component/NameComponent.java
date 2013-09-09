package org.trillek.client.entity.component;

public class NameComponent implements Component {
	
	private String name;
	
	public NameComponent(final String name) {
		this.name = name;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.NAME;
	}

	@Override
	public void cleanup() {
		this.name = null;
	}
	
	public String getName() {
		return this.name;
	}

}
