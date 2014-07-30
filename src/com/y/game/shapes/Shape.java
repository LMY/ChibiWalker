package com.y.game.shapes;

import android.graphics.Canvas;

import com.y.game.core.Vec2;
import com.y.game.scene.Camera;

public abstract class Shape
{
    protected Vec2 size;
    
	public Shape(Vec2 size)
	{
		this.size = size;
	}
	
	public abstract void draw(Vec2 src, Canvas canvas, Camera camera);
	

	public Vec2 getSize() {
		return size;
	}

	public void setSize(Vec2 size) {
		this.size = size;
	}

	public void update() {}
}
