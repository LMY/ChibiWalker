package com.y.game.entities;

import android.graphics.Canvas;

import com.y.game.core.Vec2d;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public class Item extends GameObject
{
	public Item(Vec2d position, Shape shape, Scene scene)
	{
		super(position, shape, scene);
	}

	@Override
	public void draw(Canvas canvas, Camera camera) {
		
	}
}
