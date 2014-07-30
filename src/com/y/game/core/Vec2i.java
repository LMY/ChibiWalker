package com.y.game.core;

public class Vec2i
{
	public static Vec2i ZERO = new Vec2i();
	public static Vec2i X_UNIT = new Vec2i(1, 0);
	public static Vec2i Y_UNIT = new Vec2i(0, 1);
	
	private int x;
	private int y;
	
	public Vec2i() { this(0, 0); }
	public Vec2i(Vec2i v)
	{ 
		this(v.x, v.y);
	}
	
	public Vec2i(int x, int y)
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
	
	public void sum(Vec2i v)
	{
		x += v.x;
		y += v.y;
	}
	
	public void subtract(Vec2i v)
	{
		x -= v.x;
		y -= v.y;
	}
	
	public void multiply(double k)
	{
		x *= k;
		y *= k;
	}
	
	public void dot(Vec2i v)
	{
		x *= v.x;
		y *= v.y;
	}
	
	public Vec2i lerp(Vec2i v2, double k)
	{
		if (k == 0)
			return this;
		else if (k == 1)
			return v2;
		else
			return new Vec2i((int) Math.round(x*(1-k)+v2.x*k), (int)Math.round(y*(1-k)+v2.y*k));
	}
	
	public void inverse() { multiply(-1); }
	
	public static Vec2i sum(Vec2i v1, Vec2i v2)
	{
		Vec2i r = new Vec2i(v1);
		r.sum(v2);
		return r;
	}
	public static Vec2i subtract(Vec2i v1, Vec2i v2)
	{
		Vec2i r = new Vec2i(v1);
		r.subtract(v2);
		return r;
	}
	
	public static Vec2i multiply(Vec2i v1, double k)
	{
		Vec2i r = new Vec2i(v1);
		r.multiply(k);
		return r;
	}
	
	public static Vec2i dot(Vec2i v1, Vec2i v2)
	{
		Vec2i r = new Vec2i(v1);
		r.dot(v2);
		return r;
	}
	
	public static Vec2i lerp(Vec2i v1, Vec2i v2, double k)
	{
		return v1.lerp(v2, k);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public static boolean isInside(Vec2i tl, Vec2i br, Vec2i point)
	{
		return point.getX() >= tl.getX() && point.getY() >= tl.getY() &&
				point.getX() <= br.getX() && point.getY() <= br.getY();
	}
	
	public Vec2d toVec2d() { return new Vec2d(x, y); }
}
