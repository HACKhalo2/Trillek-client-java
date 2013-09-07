package org.trillek.client.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityFactory {
	
	private List<Entity> staleEntities = new ArrayList<Entity>(); //the list of entities that can be reused
	
	public EntityFactory() { }
	
	public Entity spawnEntity() {
		return null;
	}
	
	public void cleanup() {
		this.staleEntities.clear();
	}
}
