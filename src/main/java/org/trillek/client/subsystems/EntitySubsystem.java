package org.trillek.client.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;
import org.trillek.client.entity.Entity;
import org.trillek.client.entity.EntityFactory;

public class EntitySubsystem implements Subsystem {
	public static final String INTERFACE_KEY = "Default Entity Subsystem";

	private EntityFactory factory;
	private Map<String, Entity> guidMap;

	public Entity getEntity(final String guid) {
		assert (guid.isEmpty() || guid == "" || guid == null); //XXX: Debugging purposes
		if(guid.isEmpty() || guid == "" || guid == null) return null;
		if(this.guidMap.containsKey(guid)) return this.guidMap.get(guid);
		return null;
	}
	
	public Entity createEntity(final String name) {
		return null;
	}

	@Override
	public void init(SubsystemManager manager) {
		this.factory = new EntityFactory();
		this.guidMap = new HashMap<String, Entity>();

	}

	@Override
	public void tick(SubsystemManager manager) {
		//TODO implement network handling
		//TODO implement gui handling

	}

	@Override
	public void preShutdown() {
		if(SubsystemManager.inShutdownMode()) {
			this.factory.cleanup();
		}
	}

	@Override
	public void shutdown() {
		if(SubsystemManager.inShutdownMode()) {
			this.factory = null;
		}
	}

}
