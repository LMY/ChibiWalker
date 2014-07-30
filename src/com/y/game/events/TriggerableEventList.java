package com.y.game.events;

import java.util.ArrayList;
import java.util.List;

import com.y.game.entities.GameObject;

public class TriggerableEventList extends TriggerableEvent
{
	private List<TriggerableEvent> events;
	
	public TriggerableEventList()
	{
		events = new ArrayList<TriggerableEvent>();
	}
	
	public void add(TriggerableEvent t) { events.add(t); }
	public void remove(TriggerableEvent t) { events.remove(t); }
	public int size() { return events.size(); }
	public void clear() { events.clear(); }

	@Override
	public void call(GameObject target) {
		for (TriggerableEvent te : events)
			te.call(target);
	}
}
