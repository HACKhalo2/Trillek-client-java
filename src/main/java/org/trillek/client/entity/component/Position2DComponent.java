package org.trillek.client.entity.component;

import org.lwjgl.util.vector.Vector2f;

public class Position2DComponent implements Component {
	
	private Vector2f position;
	
	public Position2DComponent(final Vector2f position) {
		this.position = position;
	}
	
	public Position2DComponent(final float x, final float y) {
		this.position = new Vector2f(x, y);
	}
	
	public Vector2f getPosition() {
		return this.position;
	}
	
	public void setX(final float x) {
		this.position.x = x;
	}
	
	public void setY(final float y) {
		this.position.y = y;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.POSITION_2D;
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

}
