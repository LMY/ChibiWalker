package com.y.game.core;

public class Vec2d
{
	public static Vec2d ZERO = new Vec2d();
	public static Vec2d X_UNIT = new Vec2d(1, 0);
	public static Vec2d Y_UNIT = new Vec2d(0, 1);
	
	private double x;
	private double y;
	
	public Vec2d() { this(0.0, 0.0); }
	public Vec2d(Vec2d v)
	{ 
		this(v.x, v.y);
	}
	
	public Vec2d(double x, double y)
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
	
	public void sum(Vec2d v)
	{
		x += v.x;
		y += v.y;
	}
	
	public void subtract(Vec2d v)
	{
		x -= v.x;
		y -= v.y;
	}
	
	public void multiply(double k)
	{
		x *= k;
		y *= k;
	}
	
	public void dot(Vec2d v)
	{
		x *= v.x;
		y *= v.y;
	}
	
	public Vec2d towards(Vec2d dest, double len)
	{
		Vec2d delta = Vec2d.subtract(dest, this);
		double deltalen = delta.length();
		if (deltalen < len)
			return dest;
		
		delta.multiply(len/deltalen);
		return sum(this, delta);
	}
	
	public Vec2d lerp(Vec2d v2, double k)
	{
		if (k == 0)
			return this;
		else if (k == 1)
			return v2;
		else
			return new Vec2d(x*(1-k)+v2.x*k, y*(1-k)+v2.y*k);
	}
	
	public void inverse() { multiply(-1); }
	
	public static Vec2d sum(Vec2d v1, Vec2d v2)
	{
		Vec2d r = new Vec2d(v1);
		r.sum(v2);
		return r;
	}
	public static Vec2d subtract(Vec2d v1, Vec2d v2)
	{
		Vec2d r = new Vec2d(v1);
		r.subtract(v2);
		return r;
	}
	
	public static Vec2d multiply(Vec2d v1, double k)
	{
		Vec2d r = new Vec2d(v1);
		r.multiply(k);
		return r;
	}
	
	public static Vec2d dot(Vec2d v1, Vec2d v2)
	{
		Vec2d r = new Vec2d(v1);
		r.dot(v2);
		return r;
	}
	
	public static Vec2d towards(Vec2d v1, Vec2d v2, double dist)
	{
		return v1.towards(v2, dist);
	}
	
	public static Vec2d lerp(Vec2d v1, Vec2d v2, double k)
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
	
	public static boolean isInside(Vec2d tl, Vec2d br, Vec2d point)
	{
		return point.getX() >= tl.getX() && point.getY() >= tl.getY() &&
				point.getX() <= br.getX() && point.getY() <= br.getY();
	}
	
	public Vec2i toVec2i() { return new Vec2i((int)Math.round(x), (int)Math.round(y)); }
}
