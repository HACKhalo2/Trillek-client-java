package org.trillek.client.entity;

import java.util.HashMap;
import java.util.Map;

import org.trillek.client.entity.component.Component;
import org.trillek.client.entity.component.ComponentType;

public class Entity {

	private final int guid;

	private Map<ComponentType, Component> components = new HashMap<ComponentType, Component>();

	protected Entity(final int guid) {
		this.guid = guid;
	}

	/**
	 * Installs the Given component into the Entity if one doesn't already exist.
	 * @param type The ComponentType this Component is
	 * @param component The Component to install;
	 * @return
	 */
	public boolean installComponent(ComponentType type, Component component) {
		if(!this.components.containsKey(type)) {
			//Check to make sure we don't place a Component into a ComponentType that isn't compatible
			if(component.getType().equals(type)) {
				this.components.put(type, component); //Add the component
				return true;
			} else return false;
		} else return false;
	}

	public int getGUID() {
		return this.guid;
	}

}
