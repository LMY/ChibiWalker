package com.y.game.events;

import java.util.ArrayList;
import java.util.List;

import com.y.game.entities.GameObject;

public class TriggerableEventListProxy extends TriggerableEvent
{
	private List<TriggerableEventList> lists;
	
	public TriggerableEventListProxy()
	{
		lists = new ArrayList<TriggerableEventList>();
	}
	
	public TriggerableEventListProxy(TriggerableEventList[] lists)
	{
		this();
		for (TriggerableEventList tl : lists)
			addList(tl);
	}
	
	public void addList(TriggerableEventList list) { lists.add(list); }
	public void removeList(TriggerableEventList list) { lists.remove(list); }
	public void clearLists() { lists.clear(); }
	
	
	public void add(TriggerableEvent t) { for (TriggerableEventList l : lists) l.add(t); }
	public void remove(TriggerableEvent t) { for (TriggerableEventList l : lists) l.remove(t); }
	public int size()
	{
		int cnt = 0;
		for (TriggerableEventList l : lists)
			cnt += l.size();
		return cnt;
	}
	
	public void clear() { for (TriggerableEventList l : lists) l.clear(); }

	@Override
	public void call(GameObject target) {
		for (TriggerableEventList l : lists)
			l.call(target);
	}
}
