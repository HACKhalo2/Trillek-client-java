package org.trillek.client.subsystems;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.trillek.client.Main;
import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;

public class EventQueueSubsystem implements Subsystem {
	public static final String INTERFACE_KEY = "Default EventQueue Driver";

	@Override
	public void init(SubsystemManager manager) { }

	@Override
	public void tick(SubsystemManager manager) {
		 //Get the Input Driver to check against input for events
		InputSubsystem input = ((InputSubsystem)manager.lookup(InputSubsystem.INTERFACE_KEY));
		//Process Window events
		if(Display.isCloseRequested()) {
			Main.log.debug("Shutting down", 1);
			GraphicsSubsystem.setRunning(false);
			return;
		}
		
		//Process Keyboard events
		for(int key : input.getRegisteredKeys()) {
			if(input.getCurrentKeyState(key)) {
				Main.log.debug("Key Pressed: "+Keyboard.getKeyName(key), 0);
				
				switch(key) {
				case Keyboard.KEY_ESCAPE:
					Main.log.debug("Shutting down", 1);
					GraphicsSubsystem.setRunning(false);
					return;
				default:
					continue;
				}
			}
		}
		
		//Process Mouse Events
		
	}

	@Override
	public void preShutdown() { }

	@Override
	public void shutdown() { }

}
