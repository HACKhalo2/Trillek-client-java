package org.trillek.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.trillek.client.driver.EventQueueDriver;
import org.trillek.client.driver.GraphicsDriver;
import org.trillek.client.driver.InputDriver;

public class Main {
	public static LogFormatter log = LogFormatter.getInstance(true);
	public SubsystemManager sSManager = new SubsystemManager();
	
	private void loadSubsystems() {
		log.debug("Injecting Subsystems...", 0);
		log.debug("Loading Graphic Driver '"+GraphicsDriver.INTERFACE_KEY+"'...", 1);
		this.sSManager.load(GraphicsDriver.INTERFACE_KEY, new GraphicsDriver());
		log.debug("Loading Input Driver '"+InputDriver.INTERFACE_KEY+"'...", 1);
		this.sSManager.load(InputDriver.INTERFACE_KEY, new InputDriver());
		log.debug("Loading Event Queue Driver '"+EventQueueDriver.INTERFACE_KEY+"'...", 1);
		this.sSManager.load(EventQueueDriver.INTERFACE_KEY, new EventQueueDriver());
		log.debug("Done", 0);
	}
	
	private Main() {
		this.loadSubsystems(); //Load up the subsystems
		this.sSManager.init(); //Initialize the Subsystem Manager and all the Subsystems
		
		//Register the Keys we are going to use
		int[] keys = new int[] {
				Keyboard.KEY_ESCAPE,
				Keyboard.KEY_UP,
				Keyboard.KEY_DOWN
		};
		((InputDriver)this.sSManager.lookup(InputDriver.INTERFACE_KEY)).registerKeys(keys, false);
		
		//Main Loop
		while(GraphicsDriver.isRunning()) {
			this.sSManager.tick(); //Tick everything
			
			//Handle Window Resizes
			if(Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			
			//Update and sync the Display
			Display.update();
			Display.sync(30);
		}
		
		this.sSManager.preShutdown();
		this.sSManager.shutdown();
	}

	public static void main(String[] args) {
		//Create the Display and check the to see if OpenGL 3 is supported
		try {
			PixelFormat format = new PixelFormat(); //TODO: Define the bare minimum PixelFormat needed to run this game
			Display.setDisplayMode(new DisplayMode(854, 480)); //9:16 display
			Display.create(format);
			
			if(!GLContext.getCapabilities().OpenGL21) {
				log.info("OpenGL 2.1 isn't supported!", 0);
				Display.destroy();
				return;
			}
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Main();
	}

}
