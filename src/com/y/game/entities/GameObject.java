package com.y.game.entities;

import android.graphics.Canvas;

import com.y.game.core.Vec2;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;
import com.y.game.utils.Randomizer;

public abstract class GameObject
{
	protected Vec2 position;
	protected Shape shape;
	protected Scene scene;
	
	public GameObject(Vec2 position, Shape shape, Scene scene)
	{
		this.position = position;
		this.shape = shape;
		this.scene = scene;
	}
	
	public abstract void draw(Canvas canvas, Camera camera);

	public void update()
	{
		if (shape != null)
			shape.update();	
	}

    public void randomizePosition(int maxx, int maxy)
    {
    	position.setX(Randomizer.DEFAULT.randInt(0, maxx));
    	position.setY(Randomizer.DEFAULT.randInt(0, maxy));
    }
    
	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
