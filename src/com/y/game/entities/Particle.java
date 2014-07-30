package com.y.game.entities;

import com.y.game.core.Vec2d;
import com.y.game.events.TriggerableEventList;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public abstract class Particle extends GameObject
{
	private int duration; 				// number of frames
	private TriggerableEventList onHit;
	
	public Particle(Vec2d position, Shape shape, Scene scene)
	{
		this(position, shape, scene, -1);
	}
	
	public Particle(Vec2d position, Shape shape, Scene scene, int duration)
	{
		super(position, shape, scene);
		
		this.onHit = new TriggerableEventList();
		this.duration = duration;
	}

	public void hit(GameObject c)
	{
		onHit.call(c);
	}
	
	@Override
	public void update(Scene scene)
	{
		if (duration > 0)
			duration--;
		
		super.update(scene);
	}
	
	public TriggerableEventList getOnHit() {
		return onHit;
	}

	public void setOnHit(TriggerableEventList onHit) {
		this.onHit = onHit;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public boolean isExpired() {
		return (this.duration <= 0);
	}
}
