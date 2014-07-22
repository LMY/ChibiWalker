package com.y.dungeon;

import android.graphics.Bitmap;

import com.y.GameView;

public class Bullet extends Sprite
{
	TriggerableEventList onHit;
	
	private int dmg;
	private Vec2 position;
	private Vec2 speed;
	
	public Bullet(GameView gameView, Bitmap bmp, int bmpcolumns, int bmprows) {
		super(gameView, bmp, bmpcolumns, bmprows);
		
		onHit = new TriggerableEventList();
		dmg = 0;
		position = new Vec2();
		speed = new Vec2();
	}
	
	@Override
	public int getAnimationRow() {
		return 0;
	}

	public void update()
	{
		position.sum(speed);
	}
	
	public void onHit(Character c)
	{
		onHit.call(c);
	}
	
	
	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getSpeed() {
		return speed;
	}

	public void setSpeed(Vec2 speed) {
		this.speed = speed;
	}
}
