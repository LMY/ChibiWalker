package com.y.dungeon;

import com.y.GameView;
import com.y.Randomizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Sprite
{
    protected int BMP_ROWS = 4;
    protected int BMP_COLUMNS = 3;
    
    protected Vec2 position;
    protected double SCALE;
    
    protected GameView gameView;
    
    protected Bitmap bmp;
    protected int currentFrame = 0;
    protected int width;
    protected int height;
    
    public Sprite(GameView gameView, Bitmap bmp, int bmpcolumns, int bmprows) {
          this.gameView = gameView;
          this.bmp = bmp;
          this.BMP_ROWS = bmprows;
          this.BMP_COLUMNS = bmpcolumns;
          
          this.width = bmp.getWidth() / BMP_COLUMNS;
          this.height = bmp.getHeight() / BMP_ROWS;
          
          SCALE = 1;
          position = new Vec2();
    }

    public void randomizePosition()
    {
    	position.setX(Randomizer.instance.randInt(0, gameView.getWidth() - width));
    	position.setY(Randomizer.instance.randInt(0, gameView.getHeight() - height));
    }


	public Vec2 getPosition() {
		return position;
	}
	public void setPosition(Vec2 position) {
		this.position = position;
	}

    
    public void update()
	{
    	currentFrame = ++currentFrame % BMP_COLUMNS;
	}

    public void draw(Canvas canvas, Vec2 scrollVector)
    {
        int ix = (int) ((position.getX()-scrollVector.getX()) * DungeonQuad.XDIM);
        int iy = (int) ((position.getY()-scrollVector.getY()) * DungeonQuad.YDIM);
        
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix + (int)(width*SCALE), iy + (int)(height*SCALE));
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public abstract int getAnimationRow();
}
