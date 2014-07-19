package com.y.dungeon;

import java.util.ArrayList;
import java.util.List;

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
	public void call(Sprite s) {
		for (TriggerableEvent te : events)
			te.call(s);
	}
}
