package org.trillek.client.driver;

import org.lwjgl.input.Keyboard;
import org.trillek.client.Main;
import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;

public class EventQueueDriver implements Subsystem {
	public static final String INTERFACE_KEY = "Default EventQueue Driver";
	private InputDriver input;

	@Override
	public void init(SubsystemManager manager) {
		this.input = ((InputDriver)manager.lookup(InputDriver.INTERFACE_KEY)); //Get the Input Driver to check against input for events
	}

	@Override
	public void tick() {
		//Very Basic Event Loop
		for(int key : this.input.getRegisteredKeys()) {
			if(this.input.getCurrentKeyState(key)) {
				Main.log.debug("Key Pressed: "+Keyboard.getKeyName(key), 0);
				if(key == Keyboard.KEY_ESCAPE) {
					Main.log.debug("Shutting down", 1);
					GraphicsDriver.setRunning(false);
				}
			}
		}
	}

	@Override
	public void preShutdown() {
		this.input = null;

	}

	@Override
	public void shutdown() { }

}
