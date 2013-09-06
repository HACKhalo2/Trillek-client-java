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
	 * @return True if the Component was installed, False otherwise.
	 */
	public boolean installComponent(ComponentType type, Component component) {
		if(!this.hasComponent(type)) {
			//Check to make sure we don't place a Component into a ComponentType that isn't compatible
			if(component.getType().equals(type)) {
				this.components.put(type, component); //Add the component
				assert this.components.containsKey(type) == true; //XXX: Assert that the key is in fact in the HashMap
				return true;
			} else return false;
		} else return false;
	}
	
	/**
	 * Checks to see if the Entity has the ComponentType installed
	 * @param type The ComponentType to check against
	 * @return True if the Entity has the ComponentType installed, False otherwise.
	 */
	public boolean hasComponent(ComponentType type) {
		return this.components.containsKey(type);
	}
	
	/**
	 * Get the component tied with the ComponentType. This function will return "null" if the Component isn't installed
	 * @param type The ComponentType to get
	 * @return The Component tied to the ComponentType, or "null" if it doesn't exist
	 */
	public Component getComponent(ComponentType type) {
		assert this.components.get(type) != null; //XXX: Assert that the value in the HashMap isn't null
		if(this.hasComponent(type)) return this.components.get(type);
		else return null;
	}
	
	/**
	 * Uninstall the specified Component linked with the ComponentType
	 * @param type The ComponentType to uninstall
	 * @return True if the Component was uninstalled, False otherwise.
	 */
	public boolean uninstallComponent(ComponentType type) {
		if(this.hasComponent(type)) {
			this.getComponent(type).cleanup(); //Cleanup the Component
			this.components.remove(type);
			assert this.components.containsKey(type) == false; //XXX: Assert that the key is not in the HashMap
			return true;
		} else return false;
	}

	public int getGUID() {
		return this.guid;
	}

}
