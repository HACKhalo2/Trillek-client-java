package org.trillek.client.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.trillek.client.entity.component.Component;
import org.trillek.client.entity.component.ComponentType;

public class Entity {
	private Map<ComponentType, Component> components;
	private final int spawnID;

	protected Entity(final int spawnID) {
		this.components = new HashMap<ComponentType, Component>();
		this.spawnID = spawnID;
	}
	
	/**
	 * Returns the Spawn ID for this Entity.
	 * @return the Spawn ID
	 */
	protected int getSpawnID() {
		return this.spawnID;
	}

	/**
	 * Installs the Given component into the Entity if one doesn't already exist.
	 * @param type The ComponentType this Component is
	 * @param component The Component to install;
	 * @return True if the Component was installed, False otherwise.
	 */
	protected boolean installComponent(ComponentType type, Component component) {
		if(!this.hasComponent(type) && type != ComponentType.BAD) { //Make sure we don't install the bad component
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
	protected boolean uninstallComponent(ComponentType type) {
		if(this.hasComponent(type)) {
			this.getComponent(type).cleanup(); //Cleanup the Component
			this.components.remove(type);
			assert this.components.containsKey(type) == false; //XXX: Assert that the key is not in the HashMap
			return true;
		} else return false;
	}
	
	/**
	 * Gets the set of ComponentTypes this Entity has installed.
	 * @return the set of ComponentTypes
	 */
	protected Set<ComponentType> getInstalledComponents() {
		return new HashSet<ComponentType>(this.components.keySet());
	}
	 /**
	  * Clean this Entities Component Map of all Keypairs stored.
	  */
	protected void clean() {
		this.components.clear();
	}

}
