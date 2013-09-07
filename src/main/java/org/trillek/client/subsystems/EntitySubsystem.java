package org.trillek.client.subsystems;

import java.util.Map;

import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;
import org.trillek.client.entity.Entity;
import org.trillek.client.entity.EntityFactory;

public class EntitySubsystem implements Subsystem {

	private EntityFactory factory;
	private Map<String, Entity> guidMap;
	private Map<String, String> nameToGuid;

	public Entity getEntity(final String name) {
		return null;
	}

	@Override
	public void init(SubsystemManager manager) {
		// TODO Auto-generated method stub

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
