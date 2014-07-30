package com.y.game.utils;

import java.util.Random;

public class Randomizer
{
	public static Randomizer DEFAULT = new Randomizer();
	
	
	private Random rand;
	
	private Randomizer()
	{
	    rand = new Random();
	}
		
	public int randInt(int min, int max)
	{
	    return min + rand.nextInt(max - min + 1);
	}
	
	public double randDouble()
	{
	    return rand.nextDouble();
	}
}
