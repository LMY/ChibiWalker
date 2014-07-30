package com.y.game.entities;

import java.util.ArrayList;
import java.util.List;

import com.y.game.core.Vec2d;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public abstract class Character extends GameObject
{
	private List<Item> equippedItems;
	private List<Item> inventoryItems;
	
	public Character(Vec2d position, Shape shape, Scene scene)
	{
		super(position, shape, scene);
		
		equippedItems = new ArrayList<Item>();
		inventoryItems = new ArrayList<Item>();
	}

	public List<Item> getEquippedItems() {
		return equippedItems;
	}

	public List<Item> getInventoryItems() {
		return inventoryItems;
	}
	
	public void loot(Item item)
	{
		inventoryItems.add(item); 
	}
	
	public boolean discard(Item item)
	{
		return inventoryItems.remove(item); 
	}
	
	public void equip(Item item)
	{
		moveItem(inventoryItems, equippedItems, item);
	}
	
	public void unequip(Item item)
	{
		moveItem(equippedItems, inventoryItems, item);
	}
	
	
	private static void moveItem(List<Item> from, List<Item> to, Item item)
	{
		if (from.contains(item)) {
			from.remove(item);
			to.add(item);
		}
	}
}
