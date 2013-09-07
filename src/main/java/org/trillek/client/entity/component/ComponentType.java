package org.trillek.client.entity.component;

public enum ComponentType {
	/**
	 * Defines a two dimensional (2D) Position Component.
	 */
	POSITION_2D,
	/**
	 * Defines a three dimensional (3D) Position Component.
	 */
	POSITION_3D,
	/**
	 * Defines a two dimensional (2D) Velocity Component.
	 */
	VELOCITY_2D,
	/**
	 * Defines a three dimensional (3D) Velocity Component.
	 */
	VELOCITY_3D,
	/**
	 * Defines a GUID Component. A GUID is a unique string given to each Entity at creation
	 */
	GUID,
	/**
	 * Defines a Bad Component. This is only used internally for the junit testing
	 */
	BAD;
}
