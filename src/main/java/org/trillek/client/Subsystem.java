package org.trillek.client;

public interface Subsystem {
	
	void init(final SubsystemManager manager);
	void tick();
	void preShutdown();
	void shutdown();

}
