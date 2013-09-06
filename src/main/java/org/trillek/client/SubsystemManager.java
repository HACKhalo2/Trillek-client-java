package org.trillek.client;

import java.util.HashMap;
import java.util.Map;

public class SubsystemManager {
	private Map<String, Subsystem> interfaceMap = new HashMap<String, Subsystem>();
	
	protected SubsystemManager() { }
	
	public void init() {
		Main.log.debug("Initializing Subsystems...", 0);
		for(Subsystem s : this.interfaceMap.values()) {
			Main.log.debug("Initializing "+s.getClass().toString(), 1);
			s.init(this);
		}
		Main.log.debug("Done!", 0);
	}
	
	public void tick() {
		for(Subsystem s : this.interfaceMap.values()) {
			s.tick(this);
		}
	}
	
	public void preShutdown() {
		for(Subsystem s : this.interfaceMap.values()) {
			s.preShutdown();
		}
	}
	
	public void shutdown() {
		for(Subsystem s : this.interfaceMap.values()) {
			s.shutdown();
		}
	}
	
	public void load(String interfaceKey, Subsystem subsystem) {
		if(this.interfaceMap.containsKey(interfaceKey)) throw new RuntimeException("Key '"+interfaceKey+"' exists in interfaceMap!");
		else {
			this.interfaceMap.put(interfaceKey, subsystem);
			Main.log.debug("Inserted "+interfaceKey+" into intrefaceMap", 3);
		}
	}
	
	public Subsystem lookup(final String key) {
		if(this.interfaceMap.containsKey(key)) return this.interfaceMap.get(key);
		else return null;
	}

}
