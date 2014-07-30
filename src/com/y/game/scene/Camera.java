package com.y.game.scene;

import com.y.game.core.Vec2;

public class Camera
{
	private Vec2 scroll;
	private Vec2 viewport;
	
	public Camera(Vec2 viewport)
	{
		this(viewport, Vec2.ZERO);
	}
	
	public Camera(Vec2 viewport, Vec2 scroll)
	{
		this.viewport = viewport;
		this.scroll = scroll;
	}

	public Vec2 getScroll() {
		return scroll;
	}

	public void setScroll(Vec2 scroll) {
		this.scroll = scroll;
	}

	public Vec2 getViewport() {
		return viewport;
	}

	public void setViewport(Vec2 viewport) {
		this.viewport = viewport;
	}

	public void update()
	{}
}
