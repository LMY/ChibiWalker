package com.y;

public class PhysicsValues
{
	public static float DT = 1.0f/30;			// "default" dt = 1/fps			
	public void verlet() { verlet(DT); }
	
	
	public float mass;
	public float kattr;
	
	public float px;
	public float py;
//	public float pz;
	
	public float vx;
	public float vy;
//	public float vz;
	
	public float fx;
	public float fy;
//	public float fz;

	public PhysicsValues(float M, float K, float x, float y /*, float z */)
	{
		mass = M;
		kattr = K;
		px = x;
		py = y;
//		pz = z;
	}
	
	public void verlet(float dt)
	{
		float ax = fx/mass;
		float ay = fy/mass;
//		float az = fz/mass;
		
		fx = fy = /*fz =*/ 0;
		
		px += vx * dt + 0.5f * ax * dt * dt;
		py += vy * dt + 0.5f * ay * dt * dt;
//		pz += vz * dt + 0.5f * az * dt * dt;
		
		vx += dt * ax;
		vy += dt * ay;
//		vz += dt * az;
	}
}
