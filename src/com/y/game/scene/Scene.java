package com.y.game.scene;

import android.graphics.Canvas;

public abstract class Scene
{
	private boolean running;
	private Camera camera;
	
	public Scene()
	{
		running = true;
	}
	
	/**
	 * @return Scene to be displayed on next frame (return "this" if you want to continue on this Scene. new Scene(...) otherwise)
	 */
	public abstract Scene nextScene(); 
	
	/**
	 * draw contained objects
	 */
	public abstract void draw(Canvas canvas);
	
	/**
	 * update the scene (update contained objects)
	 */
	public abstract void update();

	public abstract void onTouchEvent(float x, float y);
	
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
