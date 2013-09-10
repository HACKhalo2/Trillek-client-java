package org.trillek.client.test.entity;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.util.vector.Vector2f;
import org.trillek.client.entity.Entity;
import org.trillek.client.entity.EntityFactory;
import org.trillek.client.entity.component.ComponentType;
import org.trillek.client.entity.component.GUIDComponent;
import org.trillek.client.entity.component.NameComponent;
import org.trillek.client.entity.component.Position2DComponent;

public class EntityTestSuite {
	private EntityFactory factory = null;
	private Entity testEntity = null;
	private Random rgn = null;

	@Before
	public void setUpBeforeTest() throws Exception {
		this.factory = new EntityFactory();
		this.rgn = new Random(System.currentTimeMillis());
	}

	@After
	public void tearDownAfterTest() throws Exception {
		assertNotNull(this.factory);
		assertNotNull(this.testEntity);
		assertNotNull(this.rgn);
		this.factory = null;
		this.testEntity = null;
		this.rgn = null;
	}

	@Test
	public void testSpawnEntity() {
		//Set up things
		String testName = "TestEntity";
		String entityName = null;
		String guid = null;
		byte[] randomBytes = new byte[32];
		this.rgn.nextBytes(randomBytes); //seed the byte array
		String uuid = UUID.nameUUIDFromBytes(randomBytes).toString();

		//Entity Spawn. Random GUID, no name
		this.testEntity = this.factory.spawnEntity();
		System.out.println("Testing Entity Spawn, no name, random GUID");
		assertTrue(this.testEntity.hasComponent(ComponentType.GUID));
		assertTrue(this.testEntity.hasComponent(ComponentType.NAME));
		entityName = ((NameComponent)this.testEntity.getComponent(ComponentType.NAME)).getName();
		assertSame("Testing Name Match", "", entityName);
		assertFalse(this.testEntity.hasComponent(ComponentType.BAD));

		this.testEntity = null;

		//Entity Spawn. Random GUID, set Name
		this.testEntity = this.factory.spawnEntity(testName);
		System.out.println("Testing Entity Spawn, set name, random GUID");
		assertTrue(this.testEntity.hasComponent(ComponentType.GUID));
		assertTrue(this.testEntity.hasComponent(ComponentType.NAME));
		entityName = ((NameComponent)this.testEntity.getComponent(ComponentType.NAME)).getName();
		assertSame("Testing Name Match", testName, entityName);
		assertFalse(this.testEntity.hasComponent(ComponentType.BAD));

		this.testEntity = null;

		//Entity Spawn. set GUID, set Name
		this.testEntity = this.factory.spawnEntity(uuid, testName);
		System.out.println("Testing Entity Spawn, set name, set GUID");
		assertTrue(this.testEntity.hasComponent(ComponentType.GUID));
		assertTrue(this.testEntity.hasComponent(ComponentType.NAME));
		entityName = ((NameComponent)this.testEntity.getComponent(ComponentType.NAME)).getName();
		guid = ((GUIDComponent)this.testEntity.getComponent(ComponentType.GUID)).getGUID();
		assertSame("Testing Name Match", testName, entityName);
		assertSame("Testing GUID Match", uuid, guid);
		assertFalse(this.testEntity.hasComponent(ComponentType.BAD));
		
		//Tear things down
		testName = null;
		entityName = null;
		guid = null;
		randomBytes = null;
		uuid = null;
	}
	
	@Test
	public void testComponentEntity() {
		this.testEntity = this.factory.spawnEntity();
		Vector2f input = new Vector2f(10, 2);
		
		//Make the Component to test
		Position2DComponent position2d = new Position2DComponent(input);
		
		//Run a series of tests to make sure the EntityFactory.installComponent() function is working correctly
		assertTrue(this.factory.installComponent(position2d, this.testEntity));
		assertFalse(this.factory.installComponent(null, this.testEntity));
		assertFalse(this.factory.installComponent(position2d, null));
		assertFalse(this.factory.installComponent(null, null));
		
		//Make sure the Entity has the component installed
		assertTrue(this.testEntity.hasComponent(position2d.getType()));
		
		//Check to make sure that the values in the Component match
		Vector2f testVector2f = ((Position2DComponent)this.testEntity.getComponent(position2d.getType())).getPosition();
		assertTrue("Testing Vector2f (X) Match", input.x == testVector2f.x);
		assertTrue("Testing Vector2f (Y) Match", input.y == testVector2f.y);
		
		//Run a series of tests to make sure the EntityFactory.uninstallComponent() function is working correctly
		assertTrue(this.factory.uninstallComponent(position2d.getType(), this.testEntity));
		assertFalse(this.factory.uninstallComponent(ComponentType.BAD, this.testEntity));
		assertFalse(this.factory.uninstallComponent(ComponentType.BAD, null));
		assertFalse(this.factory.uninstallComponent(null, this.testEntity));
		assertFalse(this.factory.uninstallComponent(null, null));
		assertFalse(this.factory.uninstallComponent(position2d.getType(), this.testEntity));
		
		//cleanup
		input = null;
		testVector2f = null;
		position2d = null;
	}

	@Test
	public void testDespawnEntity() {
		this.testEntity = this.factory.spawnEntity();
		assertTrue(this.testEntity.hasComponent(ComponentType.GUID));
		assertTrue(this.testEntity.hasComponent(ComponentType.NAME));
		
		this.factory.despawnEntity(this.testEntity);
		assertFalse(this.testEntity.hasComponent(ComponentType.GUID));
		assertFalse(this.testEntity.hasComponent(ComponentType.NAME));
	}

}
