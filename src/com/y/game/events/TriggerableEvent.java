package com.y.game.events;
import com.y.game.entities.GameObject;

public abstract class TriggerableEvent
{
	public TriggerableEvent() {}
	public abstract void call(GameObject target);
}
