package com.y.dungeon;

import com.y.Randomizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Dungeon
{
	private Bitmap bmp;
	
	private Sprite chibi;
	
	public static int XDIM = 64;
	public static int YDIM = 64;
    private static Rect srcRect = new Rect(0, 0, XDIM, YDIM);
	
    private Vec2 scrollVector;
    
    private DungeonQuad[][] quads;
    
    private Vec2 screensize;
    
	public Dungeon(Sprite chibi, Bitmap bmp, int screenwidth, int screenheight)
	{
		this.chibi = chibi;
		this.bmp = bmp;
		
		screensize = new Vec2(screenwidth/XDIM, screenheight/YDIM);
		
		scrollVector = new Vec2();
		
//		loadLevel(DEFAULT_LVL0);
		randomLevel(100, 100);
	}
	
	
	private void loadLevel(String[] level)
	{
		quads = new DungeonQuad[level.length][];
		
        for (int y=0;y<level.length; y++) {
        	quads[y] = new DungeonQuad[level[y].length()];

        	for (int x=0; x<level[y].length(); x++)
        		if (level[y].charAt(x)=='s') {
        			chibi.setPosition(new Vec2(x, y));
        			quads[y][x] = new DungeonQuad(true);
//        			quads[y][x].onPlayerEnter(chibi);
        		}
        		else if (level[y].charAt(x)=='x') {
        			quads[y][x] = new DungeonQuad(false);
        		}
        		else if (level[y].charAt(x)==' ') {
        			quads[y][x] = new DungeonQuad(true);
        		}
        }
	}

	private void randomLevel(int dx, int dy)
	{
		quads = new DungeonQuad[dy][];
		
		boolean[][] walkable = new boolean[dy][dx];
		
		for (int y=0;y<walkable.length; y++)
			for (int x=0; x<walkable[y].length; x++)
				if (x==0||x==dx-1||y==0||y==dy-1)
					walkable[y][x] = false;
				else if (x == 2 && y == 2)
					walkable[y][x] = true;
				else {
					double dist = Math.sqrt(x*x+y*y);
					
					walkable[y][x] = (Randomizer.instance.randDouble() < 1-dist/500);
				}
				
		
//		Stack<Vec2> paths = new Stack<Vec2>();
//		paths.add(new Vec2(2, 2));
//		while (!paths.isEmpty()) {
//			Vec2 ele = paths.pop();
//			
//		}
		
		
        for (int y=0;y<quads.length; y++) {
        	quads[y] = new DungeonQuad[dx];

        	for (int x=0; x<dx; x++)
        		if (x == 2 && y == 2) {
        			chibi.setPosition(new Vec2(x, y));
        			quads[y][x] = new DungeonQuad(true);
//        			quads[y][x].onPlayerEnter(chibi);
        		}
        		else
        			quads[y][x] = new DungeonQuad(walkable[y][x]);
        }
	}
	

	public final static String[] DEFAULT_LVL0 = new String[] {
		    "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
			"xx      xx          x   x    x",
			"xx      xxxxx xxxx  x   xx xxx",
			"xx        x      x  x    x   x",
			"xx   x  xx    xxxx           x",
			"xx   x       xxx    x  xxxx xx",
			"xx   xxxxx  xxxx  xxx  xxx   x",
			"xx      xx          x  xxx   x",
			"xx      xx      s        x   x",
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
        
        int maxx = 1+(int) Math.ceil(scrollVector.getX() + screensize.getX()); 
        int maxy = 1+(int) Math.ceil(scrollVector.getY() + screensize.getY());
        
        if (maxy >= quads.length)
        	maxy = quads.length;
        if (maxx >= quads[0].length)
        	maxx = quads[0].length;
		
        for (int y=(int)scrollVector.getY();y<maxy; y++)
        	for (int x=(int)scrollVector.getX(); x<maxx; x++)
        		if (!quads[y][x].isWalkable())
	        	{
	        		int ix = x*XDIM - (int)(scrollVector.getX()*XDIM);
	        		int iy = y*YDIM - (int)(scrollVector.getY()*YDIM);

			        canvas.drawBitmap(bmp, srcRect, new Rect(ix, iy, ix + XDIM, iy + YDIM), null);
	        	}
        
        chibi.draw(canvas, scrollVector);
    }
    
    public DungeonQuad getQuad(Vec2 pos)
    {
    	int px = (int)pos.getX();
    	int py = (int)pos.getY();
    	
    	if (quads == null || py < 0 || py >= quads.length || quads[py] == null || px < 0 || px >= quads[py].length)
    		return null;
    	else
    		return quads[py][px];
    }
    
    public boolean move(Sprite sprite, Vec2 newposition)
    {
    	final DungeonQuad[] deltas = new DungeonQuad[]{ getQuad(newposition),
    										getQuad(Vec2.sum(newposition, new Vec2(0.99, 0))),
    										getQuad(Vec2.sum(newposition, new Vec2(0, 0.99))),
    										getQuad(Vec2.sum(newposition, new Vec2(0.99, 0.99))),
    	};
    	
    	for (DungeonQuad q : deltas)
    		if (q == null || !q.isWalkable())
        		return false;
    	
    	DungeonQuad fromQuad = getQuad(sprite.getPosition());
//    	if (fromQuad != toQuad) {
//	    	if (fromQuad != null)
//	    		fromQuad.onPlayerExit(sprite);
//	   		toQuad.onPlayerEnter(sprite);
//	   		sprite.setPosition(newposition);
//			return true;
//    	}
//    	else {
    		if (Vec2.subtract(sprite.getPosition(), newposition).length() > 0) {
    			sprite.setPosition(newposition);
    			return true;
    		}
    		else    		
    			return false;
//    	}
    }
    
	private void update() 
	{
		chibi.update(this);
		setScroll();
	}

	private void setScroll()
	{
		Vec2 pos = chibi.getPosition();
		
		Vec2 screenangle = Vec2.subtract(pos, Vec2.multiply(screensize, 0.5));
		scrollVector = screenangle; //Vec2.multiply(screenangle, -1);
		
		final double sx = scrollVector.getX();
		if (sx < 0)
			scrollVector.setX(0);
		else {
			final double r = quads[0].length-screensize.getX();
			if (r < 0)
				scrollVector.setX(0);
			else if (sx > r)
				scrollVector.setX(r);
		}
		
		final double sy = scrollVector.getY();
		if (sy < 0)
			scrollVector.setY(0);
		else {
			final double r = quads.length-screensize.getY();
			if (r < 0)
				scrollVector.setY(0);
			else if (sy > r)
				scrollVector.setY(r);
		}
	}


	public void onTouchEvent(float x, float y)
	{
		chibi.RequestGo((int) (x/XDIM) + (int)scrollVector.getX(), (int) (y/YDIM) + (int)scrollVector.getY());
	}
}
