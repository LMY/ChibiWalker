package com.y.dungeon;

import com.y.GameView;
import com.y.Randomizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite
{
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    
    public Vec2 position;
    
    private GameView gameView;
    
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    
    public Sprite(GameView gameView, Bitmap bmp) { this(gameView, bmp, Action.IDLE); }
    public Sprite(GameView gameView, Bitmap bmp, Action initial_action) {
          this.gameView = gameView;
          this.bmp = bmp;
          this.width = bmp.getWidth() / BMP_COLUMNS;
          this.height = bmp.getHeight() / BMP_ROWS;
          
          this.status = initial_action;
          position = new Vec2(0, 0);
          
          randomizePosition();
    }

    private void randomizePosition()
    {
    	position.setX(Randomizer.instance.randInt(0, gameView.getWidth() - width));
    	position.setY(Randomizer.instance.randInt(0, gameView.getHeight() - height));
    }

    /**
     * aggiorna lo stato dello sprite
     * @param dungeon
     * @return true se cambia qualcosa
     */
    public boolean update(Dungeon dungeon)
    {
    	if (status == Action.IDLE) {
    		return false;	// non è cambiato niente
    	}
    	else if (status == Action.GOTO) {
    		currentFrame = ++currentFrame % BMP_COLUMNS;
    		
    		Vec2 npos = position.towards(gotopos, 0.1);
    		
    		if (!dungeon.move(this, npos)) {
    			
    			// se non puoi muoverti verso la destinazione, allineati alla griglia
    			gotopos = new Vec2(Math.round(position.getX()), Math.round(position.getY()));
    			npos = position.towards(gotopos, 0.1);
    			if (dungeon.move(this, npos))
    				status = Action.IDLE;
    			
//    			Vec2 delta = Vec2.subtract(gotopos, position);
//    			boolean needup = delta.getY()<0;
//    			boolean needdown = delta.getY()>0;
//    			boolean needright = delta.getX()>0;
//    			boolean needleft = delta.getX()<0;
//    			
//    			if ((needup && dungeon.move(this, position.towards(Vec2.sum(position, new Vec2(0, -1)), 0.1))) ||
//    					(needdown && dungeon.move(this, position.towards(Vec2.sum(position, new Vec2(0, +1)), 0.1))) ||
//    					(needleft && dungeon.move(this, position.towards(Vec2.sum(position, new Vec2(-1, 0)), 0.1))) ||
//    					(needright && dungeon.move(this, position.towards(Vec2.sum(position, new Vec2(+1, 0)), 0.1))))
//    			{
//    				status = Action.IDLE;
//    			}
    		}
    		
    		//se raggiunto, cambia stato
    		if (Vec2.subtract(position, gotopos).length() < 0.01) {
    			dungeon.move(this, gotopos);
    			status = Action.IDLE;
    		}
    	}
    	
    	return true;
    }
    
    
    

	public Vec2 getPosition() {
		return position;
	}
	public void setPosition(Vec2 position) {
		this.position = position;
	}




	public enum Action { IDLE, GOTO };
    
    final static int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private int getAnimationRow()
    {
    	if (status == Action.GOTO)
    	{
          double dirDouble = (Vec2.subtract(gotopos, position).getDirection() / (Math.PI / 2) + 2);
          int direction = (int) Math.round(dirDouble) % BMP_ROWS;
          return DIRECTION_TO_ANIMATION_MAP[direction];
    	}
    	else
    		return 0;
    }
    
    public final static double SCALE = 1;

//    public void draw(Canvas canvas) { draw(canvas, 0, 0); }
    public void draw(Canvas canvas, Vec2 scrollVector)
    {
        int ix = (int) ((position.getX()-scrollVector.getX()) * Dungeon.XDIM);
        int iy = (int) ((position.getY()-scrollVector.getY()) * Dungeon.YDIM);
        
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix + (int)(width*SCALE), iy + (int)(height*SCALE));
        canvas.drawBitmap(bmp, src, dst, null);
    }
    
    public Action status = Action.IDLE;
    private Vec2 gotopos = new Vec2();

	public void RequestGo(int x2, int y2)
	{
		status = Action.GOTO;
		gotopos = new Vec2(x2, y2);
	}
}
