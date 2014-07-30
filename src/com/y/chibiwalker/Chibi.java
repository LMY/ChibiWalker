package com.y.chibiwalker;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.y.game.core.Vec2d;
import com.y.game.entities.Character;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;
import com.y.game.shapes.Sprite;

public class Chibi extends Character
{
	public Chibi(Vec2d position, Shape shape, Scene scene)
	{
		super(position, shape, scene);
	}

	@Override
	public void draw(Canvas canvas, Camera camera)
	{
		shape.draw(Vec2d.dot(position, SceneDungeon.QUADSIZE), canvas, camera);
	}
	

	/**
	 * aggiorna lo stato dello sprite
	 * 
	 * @param dungeon
	 * @return true se cambia qualcosa
	 */
	@Override
	public void update(Scene scene)
	{
		SceneDungeon dungeon = (SceneDungeon) scene;
		
		if (status == Action.IDLE)
			return; // non è cambiato niente
		else if (status == Action.GOTO) {
			super.update(scene);
			Vec2d gotopos = getNextNavPoint();
			Vec2d npos = position.towards(gotopos, 0.1);
			if (dungeon.move(this, npos)) {
				movementOk(dungeon);
				return;
			}

			// se non puoi muoverti verso la destinazione, cerca di avvicinarti
			// alla destinazione
			Vec2d delta = Vec2d.subtract(gotopos, position);

			Vec2d toUp = new Vec2d(Math.round(position.getX()),
					Math.round(position.getY() - 1));
			Vec2d toRight = new Vec2d(Math.round(position.getX() + 1),
					Math.round(position.getY()));
			Vec2d toDown = new Vec2d(Math.round(position.getX()),
					Math.round(position.getY() + 1));
			Vec2d toLeft = new Vec2d(Math.round(position.getX() - 1),
					Math.round(position.getY()));
			Vec2d[] destinations = new Vec2d[2];

			if (delta.getY() >= 0 && delta.getX() >= 0) {
				if (delta.getY() >= delta.getX()) {
					destinations[0] = toDown;
					destinations[1] = toRight;
				} else {
					destinations[0] = toRight;
					destinations[1] = toDown;
				}
			} else if (delta.getY() <= 0 && delta.getX() >= 0) {
				if (-delta.getY() >= delta.getX()) {
					destinations[0] = toUp;
					destinations[1] = toRight;
				} else {
					destinations[0] = toRight;
					destinations[1] = toUp;
				}
			} else if (delta.getY() >= 0 && delta.getX() <= 0) {
				if (delta.getY() >= -delta.getX()) {
					destinations[0] = toDown;
					destinations[1] = toLeft;
				} else {
					destinations[0] = toLeft;
					destinations[1] = toDown;
				}
			} else {
				if (-delta.getY() >= -delta.getX()) {
					destinations[0] = toUp;
					destinations[1] = toLeft;
				} else {
					destinations[0] = toLeft;
					destinations[1] = toUp;
				}
			}

			for (int i = 0; i < destinations.length; i++)
				if (dungeon.move(this, position.towards(destinations[i], 0.1))) {
					navpoints.add(0, destinations[i]);
					movementOk(dungeon);
					return;
				}

			// non è stata trovata nessuna mossa valida
			dungeon.move(this, position.towards(
					new Vec2d(Math.round(position.getX()), Math.round(position
							.getY())), 0.1));
			if (stuckframe++ == STUCKFRAMELIMIT) {
				navpoints.clear();
				stuckframe = 0;
				status = Action.IDLE;
			}
		}
	}
   
	private Vec2d getNextNavPoint()
   	{
   		return navpoints.get(0);
   	}
   
	private boolean movementOk(SceneDungeon dungeon)
	{
	   	Vec2d gotopos = getNextNavPoint();
		((Sprite)shape).setAction(getAnimationRow());

	   	if (Vec2d.subtract(position, gotopos).length() < 0.01) {
	   		dungeon.move(this, gotopos);
	   		navpoints.remove(0);
	   		
	   		if (navpoints.size() == 0)
	   			status = Action.IDLE;
	   	}
	   	
		stuckframe = 0;
		return true;
	}
       
	private int stuckframe = 0;
	private static final int STUCKFRAMELIMIT = 3;
   

	public Vec2d getPosition() {
		return position;
	}
	public void setPosition(Vec2d position) {
		this.position = position;
	}


	public enum Action { IDLE, GOTO };
	public Action status = Action.IDLE;
   
	final static int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
   
//   @Override
   public int getAnimationRow()
   {
   	if (status == Action.GOTO)
   	{
         double dirDouble = (Vec2d.subtract(getNextNavPoint(), position).getDirection() / (Math.PI / 2) + 2);
         int direction = (int) Math.round(dirDouble) % 4;
         return DIRECTION_TO_ANIMATION_MAP[direction];
   	}
   	else
   		return 0;
   }
   
	private List<Vec2d> navpoints = new ArrayList<Vec2d>();

	public void RequestGo(int x2, int y2)
	{
		status = Action.GOTO;
		navpoints.clear();
		navpoints.add(new Vec2d(x2, y2));
	}
	
	public void Attack(int x2, int y2)
	{
	
	}
}
