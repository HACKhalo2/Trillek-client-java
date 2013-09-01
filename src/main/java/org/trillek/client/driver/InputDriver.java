package org.trillek.client.driver;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.trillek.client.Main;
import org.trillek.client.Subsystem;
import org.trillek.client.SubsystemManager;
import org.trillek.client.event.MouseEvent;

/**
 * The Input Driver for the Unofficial Java Trillek Client<br />
 * <br />
 * This class uses LWJGL's Keyboard and Mouse classes internally to process it's events.
 * @author Jacob "HACKhalo2" Litewski
 */
public class InputDriver implements Subsystem {

	//The name we pass to the SubsystemManager as the key
	public static final String INTERFACE_KEY = "Default Input Driver";

	//The internal BitSets to Buffer the Keyboard events to prevent key bounces
	private BitSet bufferedIn = null, bufferedOut = null, bufferedLast = null;

	//The Map for translating the BitSet index from the key index (for the keyboard)
	private Map<Integer, Integer> keyMap = null;

	//The Set of Registered Keys we are listening too on the keyboard
	private Set<Integer> registeredKeys = null;

	private Map<MouseEvent, Integer> mouseEvents = null;

	//The internal Raw keyboard instance
	public Raw raw = new Raw();

	/**
	 * The debug class for the Input Driver
	 * @author Jacob "HACKhalo2" Litewski
	 */
	public static class Debug {
		/**
		 * Switch for if we should use the internal Buffer for the Keyboard to prevent Key bounces<br />
		 * Default: <b>True</b>
		 */
		protected static boolean useInternalKeyboardBuffer = true;

		/**
		 * Switch for if we should grab the mouse on initialization
		 * Default: <b>True</b>
		 */
		protected static boolean isMouseGrabbed = true;

		public static void shouldUseKeyboardBuffer(final boolean flag) {
			useInternalKeyboardBuffer = flag;
		}

		public static void shouldGrabMouse(final boolean flag) {
			isMouseGrabbed = flag;
		}

	}

	/**
	 * The raw input class for the InputDriver. <br />
	 * This is basically a wrapper around LWJGL's Keyboard and Mouse classes
	 * @author Jacob "HACKhalo2" Litewski
	 */
	public class Raw {
		public boolean isKeyPressed(int key) {
			return Keyboard.isKeyDown(key);
		}

		public String getKeyName(int key) {
			return Keyboard.getKeyName(key);
		}

		public boolean isButtonPressed(int button) {
			return Mouse.isButtonDown(button);
		}

		public String getButtonName(int button) {
			final String name = Mouse.getButtonName(button);
			if(name == null) {
				return "Unnamed";
			} else return name;
		}
	}

	/**
	 * Check to see if the Key is <b>currently</b> pressed.<br />
	 * Returns <b>true</b> if the key is pressed, <b>false</b> otherwise.<br />
	 * This function will always return <b>false</b> if the key being checked hasn't been registered with the Driver.
	 * @param key The LWJGL Keyboard Constant keycode
	 * @return If the key is pressed
	 */
	public boolean getCurrentKeyState(int key) {
		if(this.registeredKeys.contains(key)) {
			if(Debug.useInternalKeyboardBuffer) return this.bufferedOut.get(this.keyMap.get(key));
			else return this.raw.isKeyPressed(key);
		} else return false;
	}

	/**
	 * Check to see if the Key <b>was pressed last tick</b>.<br />
	 * Returns <b>true</b> if the key is pressed, <b>false</b> otherwise.<br />
	 * This function will always return <b>false</b> if the key being checked hasn't been registered with the Driver.
	 * @param key The LWJGL Keyboard Constant keycode
	 * @return If the key was pressed last tick
	 */
	public boolean getLastKeyState(int key) {
		if(this.registeredKeys.contains(key)) {
			if(Debug.useInternalKeyboardBuffer) return this.bufferedLast.get(this.keyMap.get(key));
			else return false;
		} else return false;
	}

	/**
	 * Register the supplied keys with the InputDriver
	 * @param keys The Set of keys to register with the Input Driver
	 * @param clearFlag Flag to clear out the keymap and registered keys, and the internal buffer, if enabled
	 */
	public void registerKeys(int[] keys, boolean clearFlag) {
		if(clearFlag) { //If the clearFlag is set, clear out the bufferedIn BitSet, the Map, and the Set for the new values
			this.bufferedIn.clear();
			this.keyMap.clear();
			this.registeredKeys.clear();
		}

		for(Integer i : keys) { //Loop through the Set
			if(!this.registeredKeys.contains(i)) { //Make sure we don't re-register the keys
				this.keyMap.put(i, this.keyMap.size());
				this.registeredKeys.add(i);
			} else Main.log.debug("Key "+Keyboard.getKeyName(i.intValue())+" was already registered, skipping...", 0);
		}
	}

	/**
	 * @return The Unmodifiable Set of registered Keyboard Keys
	 */
	public Set<Integer> getRegisteredKeys() {
		return Collections.unmodifiableSet(this.registeredKeys);
	}
	
	/**
	 * @return The Unmodifiable Set of Mouse Events
	 */
	public Map<MouseEvent, Integer> getMouseEvents() {
		return Collections.unmodifiableMap(this.mouseEvents);
	}

	@Override
	public void init(final SubsystemManager manager) {
		Main.log.info("Initalizing the Input Driver...", 0);
		//Instance the Keyboard and Mouse
		try {
			if(!Keyboard.isCreated()) Keyboard.create();
			if(!Mouse.isCreated()) Mouse.create();
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}

		//Set up the internal Buffer things (if enabled)
		if(Debug.useInternalKeyboardBuffer) {
			//Init the BitSets, the Map, and the Set
			this.bufferedIn = new BitSet();
			this.bufferedOut = new BitSet();
			this.bufferedLast = new BitSet();
		}

		this.keyMap = new TreeMap<Integer, Integer>();
		this.registeredKeys = new TreeSet<Integer>();

		//Set up the Mouse event map
		this.mouseEvents = new HashMap<MouseEvent, Integer>();

		Main.log.info("Done!", 0);
	}

	@Override
	public void tick() {
		//First, swap the bitsets around if the internal buffer is enabled
		if(Debug.useInternalKeyboardBuffer) {
			BitSet tempOut = this.bufferedOut, tempLast = this.bufferedLast;
			this.bufferedOut = this.bufferedIn; //in to out
			this.bufferedLast = tempOut; //out to last
			this.bufferedIn = tempLast; //last to in

			//Then store the current input
			for(Integer i : this.registeredKeys) {
				this.bufferedIn.set(this.keyMap.get(i), Keyboard.isKeyDown(i));
			}
		} //If the internal buffer is disabled, don't do anything with keyboard input

		//Populate the MouseEvent map (only if the mouse is grabbed)
		if(Debug.isMouseGrabbed) { //XXX: it sucks that I have to do this by hand
			if(!this.mouseEvents.isEmpty()) this.mouseEvents.clear(); //Clear out the old data if it's not empty
			this.mouseEvents.put(MouseEvent.CLICK_LEFT, (Mouse.isButtonDown(0) == true ? 1 : 0)); //Left click
			this.mouseEvents.put(MouseEvent.CLICK_RIGHT, (Mouse.isButtonDown(1) == true ? 1 : 0)); //Right click
			//TODO: Custom Mouse Button Assignments
			this.mouseEvents.put(MouseEvent.X_POS, Mouse.getX()); //X position
			this.mouseEvents.put(MouseEvent.Y_POS, Mouse.getY()); //Y position
			this.mouseEvents.put(MouseEvent.DELTA_X, Mouse.getDX()); //Delta X
			this.mouseEvents.put(MouseEvent.DELTA_Y, Mouse.getDY()); //Delta Y
			this.mouseEvents.put(MouseEvent.DELTA_WHEEL, Mouse.getDWheel()); //Delta wheel
		}
	}

	@Override
	public void preShutdown() { 
		if(Debug.useInternalKeyboardBuffer) {
			this.bufferedIn.clear();
			this.bufferedLast.clear();
			this.bufferedOut.clear();
		}

		this.keyMap.clear();
		this.registeredKeys.clear();
	}

	@Override
	public void shutdown() {
		this.bufferedIn = null;
		this.bufferedLast = null;
		this.bufferedOut = null;
		this.keyMap = null;
		this.registeredKeys = null;
	}

}
