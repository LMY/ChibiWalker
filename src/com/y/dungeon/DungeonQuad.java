package com.y.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class DungeonQuad
{
	public static int XDIM = 64;
	public static int YDIM = 64;
    private static Rect srcRect = new Rect(0, 0, XDIM, YDIM);

    private Bitmap bmpWall;
	private Vec2 position;
	private int color;

	private boolean walkable;
	
	private TriggerableEventList onPlayerEnter;
	private TriggerableEventList onPlayerStay;
	private TriggerableEventList onPlayerExit;
	
	public DungeonQuad(Bitmap bmpWall, boolean walkable, Vec2 position)
	{
		this.bmpWall = bmpWall;
		this.walkable = walkable;
		this.position = position;
		this.color = Color.BLACK;
		
		onPlayerEnter = new TriggerableEventList();
		onPlayerStay = new TriggerableEventList();
		onPlayerExit = new TriggerableEventList();
	}
	
	public void onPlayerEnter(Character player) { onPlayerEnter.call(player); }
	public void onPlayerStay(Character player) { onPlayerStay.call(player); }
	public void onPlayerExit(Character player) { onPlayerExit.call(player); }
	
	
	public void draw(Canvas canvas, Vec2 scrollVector)
	{
		final int ix = (int)Math.round((position.getX()-scrollVector.getX())*XDIM);
		final int iy = (int)Math.round((position.getY()-scrollVector.getY())*YDIM);

		if (!isWalkable())
	        canvas.drawBitmap(bmpWall, srcRect, new Rect(ix, iy, ix + XDIM, iy + YDIM), null);
		else if (color != Color.BLACK) {
			Paint myPaint = new Paint();
			myPaint.setColor(color);
//			myPaint.setStrokeWidth(10);
			canvas.drawRect(ix, iy, ix+XDIM, iy+YDIM, myPaint);
		}
	}
	

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public TriggerableEventList getOnPlayerEnter() {
		return onPlayerEnter;
	}

	public void setOnPlayerEnter(TriggerableEventList onPlayerEnter) {
		this.onPlayerEnter = onPlayerEnter;
	}

	public TriggerableEventList getOnPlayerStay() {
		return onPlayerStay;
	}

	public void setOnPlayerStay(TriggerableEventList onPlayerStay) {
		this.onPlayerStay = onPlayerStay;
	}

	public TriggerableEventList getOnPlayerExit() {
		return onPlayerExit;
	}

	public void setOnPlayerExit(TriggerableEventList onPlayerExit) {
		this.onPlayerExit = onPlayerExit;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
