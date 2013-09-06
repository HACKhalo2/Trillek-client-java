package org.trillek.client;

public interface Subsystem {
	
	void init(final SubsystemManager manager);
	void tick(final SubsystemManager manager);
	void preShutdown();
	void shutdown();

}
