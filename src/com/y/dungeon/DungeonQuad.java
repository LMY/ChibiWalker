package com.y.dungeon;

public class DungeonQuad
{
	private boolean walkable;
	
	private TriggerableEventList onPlayerEnter;
	private TriggerableEventList onPlayerStay;
	private TriggerableEventList onPlayerExit;
	
	public DungeonQuad(boolean walkable)
	{
		this.walkable = walkable;
		
		onPlayerEnter = new TriggerableEventList();
		onPlayerStay = new TriggerableEventList();
		onPlayerExit = new TriggerableEventList();
	}
	
	public void onPlayerEnter(Sprite player) { onPlayerEnter.call(player); }
	public void onPlayerStay(Sprite player) { onPlayerStay.call(player); }
	public void onPlayerExit(Sprite player) { onPlayerExit.call(player); }
	

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
