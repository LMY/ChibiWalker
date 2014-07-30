package com.y.game.scene;

import com.y.game.core.Vec2d;

public class Camera
{
	private Vec2d scroll;
	private Vec2d viewport;
	
	public Camera(Vec2d viewport)
	{
		this(viewport, Vec2d.ZERO);
	}
	
	public Camera(Vec2d viewport, Vec2d scroll)
	{
		this.viewport = viewport;
		this.scroll = scroll;
	}

	public Vec2d getScroll() {
		return scroll;
	}

	public void setScroll(Vec2d scroll) {
		this.scroll = scroll;
	}

	public Vec2d getViewport() {
		return viewport;
	}

	public void setViewport(Vec2d viewport) {
		this.viewport = viewport;
	}

	public void update()
	{}
}
