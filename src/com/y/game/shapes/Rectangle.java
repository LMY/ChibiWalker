package com.y.game.shapes;

import android.graphics.Canvas;

import com.y.game.core.Vec2d;
import com.y.game.scene.Camera;

public class Rectangle extends Shape
{
	android.graphics.Paint myPaint = new android.graphics.Paint();
	
	public Rectangle(int color, Vec2d size)
	{
		super(size);
		setColor(color);
	}
	
	public void setColor(int color)
	{
		myPaint = new android.graphics.Paint();
		myPaint.setColor(color);
	}
	
	public int getColor() { return myPaint.getColor(); }
	
	@Override
	public void draw(Vec2d src, Canvas canvas, Camera camera)
	{
		final int ix = (int) (src.getX()-camera.getScroll().getX());
		final int iy = (int) (src.getY()-camera.getScroll().getY());
		final int ix2 = (int) (ix + size.getX());
		final int iy2 = (int) (iy + size.getY());
		
		canvas.drawRect(ix, iy, ix2, iy2, myPaint);
	}
}
