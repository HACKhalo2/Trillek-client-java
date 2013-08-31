package org.trillek.client.event;

public enum MouseEvent {
	/**
	 * The Mouse's X position, clamped between 0 and Display.getWidth()-1
	 */
	X_POS,
	/**
	 * The Mouse's Y position, clamped between 0 and Display.getHeight()-1
	 */
	Y_POS,
	/**
	 * The Mouse's Delta X
	 */
	DELTA_X,
	/**
	 * The Mouse's Delta Y
	 */
	DELTA_Y,
	/**
	 * The Mouse's Wheel Delta
	 */
	DELTA_WHEEL,
	/**
	 * Left Click. 1 means it was clicked
	 */
	CLICK_LEFT,
	/**
	 * Right Click. 1 means it was clicked
	 */
	CLICK_RIGHT,
	//TODO: Custom button assignments
}
