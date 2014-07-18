package com.y;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite
{
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    public float x;
    public float y;
    public float xSpeed;
    public float ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    
    public Sprite(GameView gameView, Bitmap bmp) { this(gameView, bmp, Action.WANDER); }
    public Sprite(GameView gameView, Bitmap bmp, Action initial_action) {
          this.gameView = gameView;
          this.bmp = bmp;
          this.width = bmp.getWidth() / BMP_COLUMNS;
          this.height = bmp.getHeight() / BMP_ROWS;
          
          this.status = initial_action;
          
          randomizeSpeed();
          randomizePosition();
    }

    private void randomizeSpeed()
    {
        do {
	          xSpeed = Randomizer.instance.randInt(-5, 5);
	          ySpeed = Randomizer.instance.randInt(-5, 5);
        } while (xSpeed == 0 && ySpeed == 0);
	}

    private void randomizePosition()
    {
    	x = Randomizer.instance.randInt(0, gameView.getWidth() - width);
    	y = Randomizer.instance.randInt(0, gameView.getHeight() - height);
    }

    /**
     * aggiorna lo stato dello sprite
     * @param dungeon
     * @return true se cambia qualcosa
     */
    public boolean update(Dungeon dungeon)
    {
    	if (status == Action.WANDER) {
    		currentFrame = ++currentFrame % BMP_COLUMNS;
    		if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
    			xSpeed = -xSpeed;
    		}
    		x = x + xSpeed;
    		if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
    			ySpeed = -ySpeed;
    		}
    		y = y + ySpeed;
    	}
    	else if (status == Action.IDLE) {
    		return false;	// non è cambiato niente
    	}
    	else if (status == Action.GOTO) {
    		currentFrame = ++currentFrame % BMP_COLUMNS;
    		float dx = gotox - x;
    		float dy = gotoy - y;
    		double d = 1.0f/Math.sqrt(dx*dx+dy*dy);
    		double v = Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed);
    		
    		xSpeed = (float) (dx*d*v);
    		ySpeed = (float) (dy*d*v);
    		    		
    		if (isValidMove(dungeon))
    		{
    			x += xSpeed;
    			y += ySpeed;
    		}
    		else {
    			status = Action.IDLE;
    			xSpeed = ySpeed = 0;
    		}
    		
    		dx = gotox - x;
    		dy = gotoy - y;

    		//se raggiunto, cambia stato
    		if (dx*dx+dy*dy <= v*v)
    		{
    			x = gotox;
    			y = gotoy;

    			gotox = gotoy = 0;
    			status = Action.IDLE;    	
    			xSpeed = ySpeed = 0;
    		}
    	}
    	
    	return true;
    }
    

    private boolean isValidMove(Dungeon dungeon)
    {
//    	float newfx = x+xSpeed;
//    	float newfy = y+ySpeed;
//    	final int DX = 1; 
//    	if (!dungeon.isScreenEmpty(newfx, newfy))
//    		return false;
//    	if (!dungeon.isScreenEmpty(newfx, newfy + height - DX))
//    		return false;
//    	if (!dungeon.isScreenEmpty(newfx + width - DX, newfy))
//    		return false;
//    	if (!dungeon.isScreenEmpty(newfx + width - DX, newfy + height - DX))
//    		return false;
    	
    	int cx = (int)x;
    	int cy = (int)y;
    	int sx = (int)Math.signum(xSpeed);
    	int sy = (int)Math.signum(ySpeed);
    	
    	if (sx != 0 && !dungeon.isScreenEmpty(cx+sx, cy))
    		return false;
    	
    	if (sy != 0 && !dungeon.isScreenEmpty(cx, cy+sy))
    		return false;
    	
    	if (sx != 0 && sy != 0 && !dungeon.isScreenEmpty(cx+sx, cy+sy))
    		return false;
    	
    	return true;
	}

    
    
    

	public enum Action { WANDER, IDLE, GOTO };
    
    final static int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private int getAnimationRow()
    {
    	if (status == Action.WANDER || status == Action.GOTO)
    	{
          double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
          int direction = (int) Math.round(dirDouble) % BMP_ROWS;
          return DIRECTION_TO_ANIMATION_MAP[direction];
    	}
//    	else if (status == Action.GOTO)
//    	{
//            double dirDouble = (Math.atan2(gotox-x, gotoy-y) / (Math.PI / 2) + 2);
//            int direction = (int) Math.round(dirDouble) % BMP_ROWS;
//            return DIRECTION_TO_ANIMATION_MAP[direction];
//    	}
//    	else if (status == Action.IDLE)
//    		return 0;
    	else
    		return 0;
    }
    
    public final static double SCALE = 1;

//    public void draw(Canvas canvas) { draw(canvas, 0, 0); }
    public void draw(Canvas canvas, float deltaX, float deltaY)
    {
        int ix = Math.round(x + deltaX);
        int iy = Math.round(y + deltaY);
        
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix + (int)(width*SCALE), iy + (int)(height*SCALE));
        canvas.drawBitmap(bmp, src, dst, null);
    }
    
    public Action status = Action.WANDER;
    private int gotox = 0;
    private int gotoy = 0;

	public void RequestGo(int x2, int y2)
	{
		status = Action.GOTO;
		gotox = x2;
		gotoy = y2;
		xSpeed = 5;
		ySpeed = 0;
	}
}
