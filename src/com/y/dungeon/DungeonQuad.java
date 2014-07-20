package com.y.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class DungeonQuad
{
	public static int XDIM = 64;
	public static int YDIM = 64;
    private static Rect srcRect = new Rect(0, 0, XDIM, YDIM);

    private Bitmap bmpWall;
	private Vec2 position;

	private boolean walkable;
	
	private TriggerableEventList onPlayerEnter;
	private TriggerableEventList onPlayerStay;
	private TriggerableEventList onPlayerExit;
	
	public DungeonQuad(Bitmap bmpWall, boolean walkable, Vec2 position)
	{
		this.bmpWall = bmpWall;
		this.walkable = walkable;
		this.position = position;
		
		onPlayerEnter = new TriggerableEventList();
		onPlayerStay = new TriggerableEventList();
		onPlayerExit = new TriggerableEventList();
	}
	
	public void onPlayerEnter(Sprite player) { onPlayerEnter.call(player); }
	public void onPlayerStay(Sprite player) { onPlayerStay.call(player); }
	public void onPlayerExit(Sprite player) { onPlayerExit.call(player); }
	
	
	public void draw(Canvas canvas, Vec2 scrollVector)
	{
		if (!isWalkable()) {
    		final int ix = (int)Math.round((position.getX()-scrollVector.getX())*XDIM);
    		final int iy = (int)Math.round((position.getY()-scrollVector.getY())*YDIM);

	        canvas.drawBitmap(bmpWall, srcRect, new Rect(ix, iy, ix + XDIM, iy + YDIM), null);
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
}
