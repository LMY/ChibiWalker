package com.y.game.entities.ui;


import com.y.game.core.Vec2;
import com.y.game.entities.GameObject;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public abstract class UIElement extends GameObject
{
	private boolean visible;
	private boolean enabled;
	
	public UIElement(Vec2 position, Shape shape, Scene scene)
	{
		super(position, shape, scene);
		
		visible = true;
		enabled = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

//	@Override
//	public void draw(Vec2 src, Vec2 size, Canvas canvas, Camera camera)
//	{
//		if (visible)
//			super.draw(src, size, canvas, camera);
//	}
}
