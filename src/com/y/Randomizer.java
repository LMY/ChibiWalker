package com.y;

import java.util.Random;

public class Randomizer
{
	public static Randomizer instance;
	
	public static void init() { instance = new Randomizer(); }
	
	
	private Random rand;
	
	private Randomizer()
	{
	    rand = new Random();
	}
		
	public int randInt(int min, int max)
	{
	    return min + rand.nextInt(max - min + 1);
	}
}
