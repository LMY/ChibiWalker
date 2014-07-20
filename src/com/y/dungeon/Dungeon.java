package com.y.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.y.Randomizer;
import com.y.ui.UIButton;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Dungeon
{
	private Sprite chibi;
	
    private Vec2 scrollVector;
    
    private DungeonQuad[][] quads;
    
    private Vec2 screensize;
    
	public Dungeon(Sprite chibi, Bitmap bmpWall, int screenwidth, int screenheight)
	{
		this.chibi = chibi;
		
		screensize = new Vec2(screenwidth/DungeonQuad.XDIM, screenheight/DungeonQuad.YDIM);
		
		scrollVector = new Vec2();
		
		initUIButtons();
		
//		loadLevel(DEFAULT_LVL0);
		randomLevel(bmpWall, 100, 100);
	}
	
	
	private List<UIButton> uiButtons;
	public void initUIButtons()
	{
		final int SPACING = 10;
		
		uiButtons = new ArrayList<UIButton>();
		uiButtons.add(UIButton.buttonUp);
		UIButton.buttonUp.setPosition(new Vec2(SPACING+DungeonQuad.XDIM*1, screensize.getY()*DungeonQuad.YDIM-SPACING-DungeonQuad.XDIM));
		
		uiButtons.add(UIButton.buttonDown);
		UIButton.buttonDown.setPosition(new Vec2(SPACING+DungeonQuad.XDIM*1, screensize.getY()*DungeonQuad.YDIM-SPACING));
		
		uiButtons.add(UIButton.buttonLeft);
		UIButton.buttonLeft.setPosition(new Vec2(SPACING, screensize.getY()*DungeonQuad.YDIM-SPACING));
		
		uiButtons.add(UIButton.buttonRight);
		UIButton.buttonRight.setPosition(new Vec2(SPACING+DungeonQuad.XDIM*2, screensize.getY()*DungeonQuad.YDIM-SPACING));
		
		uiButtons.add(UIButton.buttonAttack);
		UIButton.buttonAttack.setPosition(new Vec2(SPACING+DungeonQuad.XDIM*2+SPACING, screensize.getY()*DungeonQuad.YDIM-SPACING-DungeonQuad.XDIM-SPACING));
	}
	


	private void randomLevel(Bitmap bmpWall, int dx, int dy)
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
				

        for (int y=0;y<quads.length; y++) {
        	quads[y] = new DungeonQuad[dx];

        	for (int x=0; x<dx; x++) {
        		final Vec2 position = new Vec2(x, y);
        		
        		if (x == 2 && y == 2) {
        			chibi.setPosition(new Vec2(x, y));
        			quads[y][x] = new DungeonQuad(bmpWall, true, position);
//        			quads[y][x].onPlayerEnter(chibi);
        		}
        		else
        			quads[y][x] = new DungeonQuad(bmpWall, walkable[y][x], position);
        	}
        }
	}
	

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
        		quads[y][x].draw(canvas, scrollVector);
        
        chibi.draw(canvas, scrollVector);
        
        for (UIButton b : uiButtons)
        	b.draw(canvas, scrollVector);
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

	
	public boolean affectedByTouch(Sprite s, int x, int y)
	{
		Vec2 pos = s.getPosition();
		Vec2 maxps = Vec2.sum(pos, new Vec2(s.width, s.height));
		
		return (x <= maxps.getX() && x >= pos.getX() && y <= maxps.getY() && y >= pos.getY());
	}
	
	public void onTouchEvent(float x, float y)
	{
		boolean done = false;
		
		for (UIButton b : uiButtons)
			if (affectedByTouch(b, (int)x, (int) y)) {// in screen coordinates
				b.onTouchEvent((int)x, (int) y); 		
				done = true;
			}
		
		if (!done) {
			final int screenX = (int) (x/DungeonQuad.XDIM) + (int)scrollVector.getX();
			final int screenY = (int) (y/DungeonQuad.YDIM) + (int)scrollVector.getY();
			chibi.RequestGo(screenX, screenY); // in game coordinate
		}
	}
}
