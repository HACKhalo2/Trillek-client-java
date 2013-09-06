package org.trillek.client.subsystems;

import org.lwjgl.opengl.GL11;
import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;

public class GraphicsSubsystem implements Subsystem {
	public static final String INTERFACE_KEY = "Default Graphics Driver";
	protected static boolean running = false;
	
	public static boolean isRunning() {
		return running;
	}
	
	protected static void setRunning(final boolean flag) {
		running = flag;
	}

	@Override
	public void init(SubsystemManager manager) {
		running = true;
	}

	@Override
	public void tick(SubsystemManager manager) {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void preShutdown() {
		//XXX Nothing to do now

	}

	@Override
	public void shutdown() {
		//XXX Nothing to do now

	}

}
