package com.y.game.core;

public class Vec2
{
	public static Vec2 ZERO = new Vec2();
	public static Vec2 X_UNIT = new Vec2(1, 0);
	public static Vec2 Y_UNIT = new Vec2(0, 1);
	
	private double x;
	private double y;
	
	public Vec2() { this(0.0, 0.0); }
	public Vec2(Vec2 v)
	{ 
		this(v.x, v.y);
	}
	
	public Vec2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getDirection()
	{
		return Math.atan2(x, y);
	}
	
	public double length()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public void sum(Vec2 v)
	{
		x += v.x;
		y += v.y;
	}
	
	public void subtract(Vec2 v)
	{
		x -= v.x;
		y -= v.y;
	}
	
	public void multiply(double k)
	{
		x *= k;
		y *= k;
	}
	
	public void dot(Vec2 v)
	{
		x *= v.x;
		y *= v.y;
	}
	
	public Vec2 towards(Vec2 dest, double len)
	{
		Vec2 delta = Vec2.subtract(dest, this);
		double deltalen = delta.length();
		if (deltalen < len)
			return dest;
		
		delta.multiply(len/deltalen);
		return sum(this, delta);
	}
	
	public Vec2 lerp(Vec2 v2, double k)
	{
		if (k == 0)
			return this;
		else if (k == 1)
			return v2;
		else
			return new Vec2(x*(1-k)+v2.x*k, y*(1-k)+v2.y*k);
	}
	
	public void inverse() { multiply(-1); }
	
	public static Vec2 sum(Vec2 v1, Vec2 v2)
	{
		Vec2 r = new Vec2(v1);
		r.sum(v2);
		return r;
	}
	public static Vec2 subtract(Vec2 v1, Vec2 v2)
	{
		Vec2 r = new Vec2(v1);
		r.subtract(v2);
		return r;
	}
	
	public static Vec2 multiply(Vec2 v1, double k)
	{
		Vec2 r = new Vec2(v1);
		r.multiply(k);
		return r;
	}
	
	public static Vec2 dot(Vec2 v1, Vec2 v2)
	{
		Vec2 r = new Vec2(v1);
		r.dot(v2);
		return r;
	}
	
	public static Vec2 towards(Vec2 v1, Vec2 v2, double dist)
	{
		return v1.towards(v2, dist);
	}
	
	public static Vec2 lerp(Vec2 v1, Vec2 v2, double k)
	{
		return v1.lerp(v2, k);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public static boolean isInside(Vec2 tl, Vec2 br, Vec2 point)
	{
		return point.getX() >= tl.getX() && point.getY() >= tl.getY() &&
				point.getX() <= br.getX() && point.getY() <= br.getY();
	}
}
