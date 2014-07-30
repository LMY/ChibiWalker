package com.y.chibiwalker;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.y.game.core.Vec2;
import com.y.game.entities.Character;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Shape;

public class Chibi extends Character
{
	public Chibi(Vec2 position, Shape shape, Scene scene)
	{
		super(position, shape, scene);
	}

	@Override
	public void draw(Canvas canvas, Camera camera)
	{
		shape.draw(Vec2.dot(position, SceneDungeon.QUADSIZE), canvas, camera);
	}
	

	/**
	 * aggiorna lo stato dello sprite
	 * 
	 * @param dungeon
	 * @return true se cambia qualcosa
	 */
	@Override
	public void update(/*SceneDungeon dungeon*/)
	{
		if (status == Action.IDLE)
			return; // non � cambiato niente
		else if (status == Action.GOTO) {
//			super.update();
//			Vec2 gotopos = getNextNavPoint();
//			Vec2 npos = position.towards(gotopos, 0.1);
//			if (dungeon.move(this, npos)) {
//				movementOk(dungeon);
//				return;
//			}
//
//			// se non puoi muoverti verso la destinazione, cerca di avvicinarti
//			// alla destinazione
//			Vec2 delta = Vec2.subtract(gotopos, position);
//
//			Vec2 toUp = new Vec2(Math.round(position.getX()),
//					Math.round(position.getY() - 1));
//			Vec2 toRight = new Vec2(Math.round(position.getX() + 1),
//					Math.round(position.getY()));
//			Vec2 toDown = new Vec2(Math.round(position.getX()),
//					Math.round(position.getY() + 1));
//			Vec2 toLeft = new Vec2(Math.round(position.getX() - 1),
//					Math.round(position.getY()));
//			Vec2[] destinations = new Vec2[2];
//
//			if (delta.getY() >= 0 && delta.getX() >= 0) {
//				if (delta.getY() >= delta.getX()) {
//					destinations[0] = toDown;
//					destinations[1] = toRight;
//				} else {
//					destinations[0] = toRight;
//					destinations[1] = toDown;
//				}
//			} else if (delta.getY() <= 0 && delta.getX() >= 0) {
//				if (-delta.getY() >= delta.getX()) {
//					destinations[0] = toUp;
//					destinations[1] = toRight;
//				} else {
//					destinations[0] = toRight;
//					destinations[1] = toUp;
//				}
//			} else if (delta.getY() >= 0 && delta.getX() <= 0) {
//				if (delta.getY() >= -delta.getX()) {
//					destinations[0] = toDown;
//					destinations[1] = toLeft;
//				} else {
//					destinations[0] = toLeft;
//					destinations[1] = toDown;
//				}
//			} else {
//				if (-delta.getY() >= -delta.getX()) {
//					destinations[0] = toUp;
//					destinations[1] = toLeft;
//				} else {
//					destinations[0] = toLeft;
//					destinations[1] = toUp;
//				}
//			}
//
//			for (int i = 0; i < destinations.length; i++)
//				if (dungeon.move(this, position.towards(destinations[i], 0.1))) {
//					navpoints.add(0, destinations[i]);
//					movementOk(dungeon);
//					return;
//				}
//
//			// non � stata trovata nessuna mossa valida
//			dungeon.move(this, position.towards(
//					new Vec2(Math.round(position.getX()), Math.round(position
//							.getY())), 0.1));
//			if (stuckframe++ == STUCKFRAMELIMIT) {
//				navpoints.clear();
//				stuckframe = 0;
//				status = Action.IDLE;
//			}
		}
	}
   
//   private Vec2 getNextNavPoint()
//   {
//   	return navpoints.get(0);
//   }
//   
//   private boolean movementOk(SceneDungeon dungeon)
//   {
//   	Vec2 gotopos = getNextNavPoint();
//   	
//   	if (Vec2.subtract(position, gotopos).length() < 0.01) {
//   		dungeon.move(this, gotopos);
//   		navpoints.remove(0);
//   		
//   		if (navpoints.size() == 0)
//   			status = Action.IDLE;
//   	}
//   	
//		stuckframe = 0;
//		return true;
//   }
//       
//   private int stuckframe = 0;
//   private static final int STUCKFRAMELIMIT = 3;
   

	public Vec2 getPosition() {
		return position;
	}
	public void setPosition(Vec2 position) {
		this.position = position;
	}


	public enum Action { IDLE, GOTO };
   public Action status = Action.IDLE;
   
   final static int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
   
//   @Override
//   public int getAnimationRow()
//   {
//   	if (status == Action.GOTO)
//   	{
//         double dirDouble = (Vec2.subtract(getNextNavPoint(), position).getDirection() / (Math.PI / 2) + 2);
//         int direction = (int) Math.round(dirDouble) % BMP_ROWS;
//         return DIRECTION_TO_ANIMATION_MAP[direction];
//   	}
//   	else
//   		return 0;
//   }
   
   private List<Vec2> navpoints = new ArrayList<Vec2>();

	public void RequestGo(int x2, int y2)
	{
		status = Action.GOTO;
		navpoints.clear();
		navpoints.add(new Vec2(x2, y2));
	}
	
	public void Attack(int x2, int y2)
	{
	
	}
}