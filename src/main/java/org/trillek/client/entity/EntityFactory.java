package org.trillek.client.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.trillek.client.entity.component.ComponentType;
import org.trillek.client.entity.component.GUIDComponent;
import org.trillek.client.entity.component.NameComponent;

public class EntityFactory {

	private List<Entity> reusableEntities; //the list of entities that can be reused
	private Random god;
	private AtomicInteger spawnCounter;

	/**
	 * Creates the EntityFactory with the RGN seeded with the current Millisecond time of the computer
	 */
	public EntityFactory() {
		this.reusableEntities = new ArrayList<Entity>();
		this.god = new Random(System.currentTimeMillis());
		this.spawnCounter = new AtomicInteger();
	}

	/**
	 * Spawns an Entity with a random GUID (generated from a random 32 byte array fed into the UUID generator)<br />
	 * and no name.
	 * @return The Spawned Entity Container
	 */
	public Entity spawnEntity() {
		return this.spawnEntity("");
	}

	public Entity spawnEntity(final String name) {
		//Generate the GUID
		byte[] seed = new byte[32];
		this.god.nextBytes(seed);
		UUID uuid = UUID.nameUUIDFromBytes(seed);

		return this.spawnEntity(uuid.toString(), name);
	}

	public Entity spawnEntity(final String guid, final String name) {
		Entity entity = null;
		if(!this.reusableEntities.isEmpty()) {
			entity = this.reusableEntities.get(0); //Grab an entity out of the reusable Entity list
			//TODO: Uninstall the StaleComponent
			entity.clean(); //make sure the entity has nothing installed
		} else entity = new Entity(this.spawnCounter.getAndIncrement());

		//Configure and install the GUID component into the Entity
		GUIDComponent guidComponent = new GUIDComponent(guid);
		entity.installComponent(ComponentType.GUID, guidComponent);

		//Configure and install the Name Component into the Entity
		NameComponent nameComponent = new NameComponent(name);
		entity.installComponent(ComponentType.NAME, nameComponent);

		return entity;
	}

	/**
	 * Despawns the Entity passed into this function. 
	 * @param entity The Entity to despawn
	 */
	public void despawnEntity(Entity entity) {
		for(ComponentType type : entity.getInstalledComponents()) {
			entity.uninstallComponent(type); //Uninstall all the components this entity has
		}
		entity.clean(); //Final sweep of the Entity to make use it's not holding onto any memory allocations
		this.reusableEntities.add(entity); //Add the entity to the resuableEntities list
	}

	public void cleanup() {
		this.reusableEntities.clear();
		this.god = null;
	}
}
