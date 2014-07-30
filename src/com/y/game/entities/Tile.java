package com.y.game.entities;

import android.graphics.Canvas;

import com.y.game.core.Vec2;
import com.y.game.events.TriggerableEventList;
import com.y.game.events.TriggerableEventListProxy;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public class Tile extends GameObject
{
	public Tile(Vec2 position, Shape shape, Scene scene, boolean walkable)
	{
		super(position, shape, scene);

		this.walkable = walkable;
		
		onPlayerEnter = new TriggerableEventList();
		onPlayerStay = new TriggerableEventList();
		onPlayerExit = new TriggerableEventList();
		
		onEnemyEnter = new TriggerableEventList();
		onEnemyStay = new TriggerableEventList();
		onEnemyExit = new TriggerableEventList();
		
		onCreatureEnter = new TriggerableEventListProxy(new TriggerableEventList[] { onPlayerEnter, onEnemyEnter});
		onCreatureStay = new TriggerableEventListProxy(new TriggerableEventList[] { onPlayerStay, onEnemyStay});
		onCreatureExit = new TriggerableEventListProxy(new TriggerableEventList[] { onPlayerExit, onEnemyExit});
	}
	
	private boolean walkable;
	
	private TriggerableEventList onPlayerEnter;
	private TriggerableEventList onPlayerStay;
	private TriggerableEventList onPlayerExit;

	private TriggerableEventList onEnemyEnter;
	private TriggerableEventList onEnemyStay;
	private TriggerableEventList onEnemyExit;
	
	private TriggerableEventListProxy onCreatureEnter;
	private TriggerableEventListProxy onCreatureStay;
	private TriggerableEventListProxy onCreatureExit;
	
	public void onPlayerEnter(Character player) { onPlayerEnter.call(player); }
	public void onPlayerStay(Character player) { onPlayerStay.call(player); }
	public void onPlayerExit(Character player) { onPlayerExit.call(player); }
	
	public void onEnemyEnter(Character player) { onEnemyEnter.call(player); }
	public void onEnemyStay(Character player) { onEnemyStay.call(player); }
	public void onEnemyExit(Character player) { onEnemyExit.call(player); }
	
	public void onCreatureEnter(Character player) { onCreatureEnter.call(player); }
	public void onCreatureStay(Character player) { onCreatureStay.call(player); }
	public void onCreatureExit(Character player) { onCreatureExit.call(player); }

	
	public void draw(Canvas canvas, Camera camera)
	{
		shape.draw(Vec2.dot(position, shape.getSize()), canvas, camera);
	}
	
	@Override
	public void update()
	{
		//TODO: Tile::update()
		//shape.update();
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
