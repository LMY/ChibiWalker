package com.y.game.shapes;

import android.graphics.Canvas;

import com.y.game.core.Vec2d;
import com.y.game.scene.Camera;

public abstract class Shape
{
    protected Vec2d size;
    
	public Shape(Vec2d size)
	{
		this.size = size;
	}
	
	public abstract void draw(Vec2d src, Canvas canvas, Camera camera);
	

	public Vec2d getSize() {
		return size;
	}

	public void setSize(Vec2d size) {
		this.size = size;
	}

	public void update() {}
}
