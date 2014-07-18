package com.y;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Dungeon
{
	private Bitmap bmp;
	
	private Sprite chibi;
	private List<Sprite> pgs;
	private String[] level;
	
	public static int XDIM = 64;
	public static int YDIM = 64;
    private static Rect srcRect = new Rect(0, 0, XDIM, YDIM);
	
    private Object syncPgs;
    
    
    private int scrollX; 
    private int scrollY;
    
    private int width;
    private int height;
    
	public Dungeon(Sprite chibi, Bitmap bmp, int screenwidth, int screenheight)
	{
		this.chibi = chibi;
		this.bmp = bmp;
		level = DEFAULT_LVL0;
		pgs = new ArrayList<Sprite>();
		
		width = screenwidth;
		height = screenheight;
		scrollX = 0;
		scrollY = 0;
		
		syncPgs = new Object();
		
        for (int y=0;y<level.length; y++)
        	for (int x=0; x<level[y].length(); x++)
        		if (level[y].charAt(x)=='s')
        		{
        			this.chibi.x = x*XDIM;
        			this.chibi.y = y*YDIM;
        		}
	}
	
	
    
    public void addSprite(Sprite s)
    {
    	synchronized (syncPgs)
    	{
    		pgs.add(s);    		
    	}
    }
    
    public void removeSprite(Sprite s)
    {
    	synchronized (syncPgs)
    	{
    		pgs.remove(s);    		
    	}
    }
    
	
	public void loadLevel(String[] lvl) { level = lvl; }

	
	public final static String[] DEFAULT_LVL0 = new String[] {
		    "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
			"xx      xx          x   x    x",
			"xx s    xxxxx xxxx  x   xx xxx",
			"xx        x      x  x    x   x",
			"xx   x  xx    xxxx           x",
			"xx   x       xxx    x  xxxx xx",
			"xx   xxxxx  xxxx  xxx  xxx   x",
			"xx      xx          x  xxx   x",
			"xx      xx               x   x",
			"xx  xxxxxxxxxxx  xxxxxxxxx   x",
			"xx  xxx                      x",
			"xx       xx      xxxx        x",
			"xx  xxxxxxxxxxxxxxxxxxxxx xxxx",
			"xx  xxx         x            x",
			"xx  xxx  xxxx   x  x  xxx    x",
			"xx  xxx  x  x   x  x  x      x",
			"xx  xxx  x  xx  xx xxxx x    x",
			"xx       x              x    x",
			"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"};

    public void draw(Canvas canvas)
    {
        update();
		checkCapture();
		
        for (int y=0;y<level.length; y++)
        	for (int x=0; x<level[y].length(); x++)
        		if (level[y].charAt(x)=='x')
	        	{
	        		int ix = x*XDIM - (int)scrollX;
	        		int iy = y*YDIM - (int)scrollY;

			        canvas.drawBitmap(bmp, srcRect, new Rect(ix, iy, ix + XDIM, iy + YDIM), null);
	        	}
        
        chibi.draw(canvas, -scrollX, -scrollY);
        
    	synchronized (syncPgs)
    	{
    		for (Sprite s : pgs)
    			s.draw(canvas, -scrollX, -scrollY);
    	}
    }

	
    private void checkCapture()
    {
    	synchronized (syncPgs)
    	{
    		for (Iterator<Sprite> iter = pgs.iterator(); iter.hasNext();)
    		{
    			Sprite s = iter.next();

    			// intersect?
    			if (chibi.x >= s.x && chibi.x <= s.x + 64  &&  chibi.y >= s.y && chibi.y <= s.y + 64 ||
    					chibi.x <= s.x && chibi.x >= s.x - 64  &&  chibi.y <= s.y && chibi.y >= s.y - 64)
    				iter.remove();
    		}	
    	}
	}

    
    public boolean isEmpty(int x, int y)
    {
    	return level[y].charAt(x)!='x';
    }
    
    /**
     * Se la cella che sta alle coordinate su schermo (x,y) è vuota
     * @param x
     * @param y
     * @return
     */
//    public boolean isScreenEmpty(float x, float y)
//    {
////    	return isScreenEmpty((int) x, (int) y);
//    	if (//!isScreenEmpty((int)Math.ceil(x), (int)Math.ceil(y)) ||
//    		!isScreenEmpty((int)Math.ceil(x), (int)Math.floor(y)) ||
//    		!isScreenEmpty((int)Math.floor(x), (int)Math.ceil(y)) ||
//    		!isScreenEmpty((int)Math.floor(x), (int)Math.floor(y)))
//    		return false;
//    	
//    	return true;
//    }
    
    public boolean isScreenEmpty(int x, int y)
    {
    	try {
    		char c = level[y/YDIM].charAt(x/XDIM);
    		return c !='x';
    	}
    	catch (Exception e) { return false; }
    }
    
	private void update() 
	{
		if (chibi.update(this))
			setScroll();
		
    	synchronized (syncPgs)
    	{
    		for (Sprite s : pgs)
    			s.update(this);
    	}
	}

	private void setScroll()
	{
		scrollY = (int)(chibi.y - height/2);
		scrollX = (int)(chibi.x - width/2);
		
		if (scrollY < 0)
			scrollY = 0;
		else if (scrollY > level.length*YDIM - height-1)
			scrollY = level.length*YDIM - height-1;
		
		
		if (scrollX < 0)
			scrollX = 0;
		else if (scrollX > level[0].length()*XDIM - width-1)
			scrollX = level[0].length()*XDIM - width-1;
	}



	public void RequestPlayerGo(float x, float y) {
		chibi.RequestGo((int)(x+scrollX)/XDIM * XDIM, (int)(y+scrollY)/YDIM * YDIM);
	}
}
