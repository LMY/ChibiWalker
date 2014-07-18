package com.y;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Drawable
{
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private Bitmap bmp;
    protected int currentFrame = 0;
    protected int width;
    protected int height;

    public Drawable(Bitmap bmp)
    {
          this.bmp = bmp;
          this.width = bmp.getWidth() / BMP_COLUMNS;
          this.height = bmp.getHeight() / BMP_ROWS;
    }

    public void update()
    {
    	++currentFrame;
    	doUpdate();	
    }
    
    protected abstract void doUpdate();
    
    final static int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private int getAnimationRow(float dx, float dy)
    {
        double dirDouble = (Math.atan2(dx, dy) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public void draw(Canvas canvas, float x, float y, float dx, float dy)
    {
        update();
        
        int ix = Math.round(x);
        int iy = Math.round(y);
        
        int srcX = currentFrame * width;
        int srcY = getAnimationRow(dx, dy) * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix + width, iy + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

	public Bitmap getBmp() {
		return bmp;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
