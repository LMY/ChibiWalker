package com.y.dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.y.Randomizer;
import com.y.ui.UIButton;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class Dungeon
{
	private Character chibi;
	private List<Bullet> bullets;
	
    private Vec2 scrollVector;
    
    private DungeonQuad[][] quads;
    
    private Vec2 screensize;
    
	public Dungeon(Character chibi, Bitmap bmpWall, int screenwidth, int screenheight)
	{
		this.chibi = chibi;
		
		bullets = new ArrayList<Bullet>();
		
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
       			quads[y][x] = new DungeonQuad(bmpWall, walkable[y][x], position);
       			final DungeonQuad q = quads[y][x];
       			quads[y][x].getOnPlayerEnter().add(new TriggerableEvent() { public void call(Character s) { q.setColor(Color.GREEN); } });
       			quads[y][x].getOnPlayerStay().add(new TriggerableEvent() { public void call(Character s) { q.setColor(Color.RED); } });
       			quads[y][x].getOnPlayerExit().add(new TriggerableEvent() { public void call(Character s) { q.setColor(Color.BLACK); } });
       			
        		if (x == 2 && y == 2) {
        			chibi.setPosition(position);
//        			quads[y][x].onPlayerEnter(chibi);
        		}
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
        
        for (Bullet b : bullets)
        	b.draw(canvas, scrollVector);
        
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
    
    public boolean move(Character sprite, Vec2 newposition)
    {
    	// quadrati destinazione
    	Set<DungeonQuad> toQuads = new HashSet<DungeonQuad>();
    	toQuads.add(getQuad(newposition));
    	toQuads.add(getQuad(Vec2.sum(newposition, new Vec2(0.99, 0))));
    	toQuads.add(getQuad(Vec2.sum(newposition, new Vec2(0, 0.99))));
    	toQuads.add(getQuad(Vec2.sum(newposition, new Vec2(0.99, 0.99))));
    	
    	// se non è movimento in un quadrato vuoto, abort
    	for (DungeonQuad q : toQuads)
    		if (q == null || !q.isWalkable())
        		return false;
    	
    	// quadrati attualmente occupati
    	final Vec2 spritePos = sprite.getPosition();
    	Set<DungeonQuad> nowQuads = new HashSet<DungeonQuad>();
    	nowQuads.add(getQuad(spritePos));
    	nowQuads.add(getQuad(Vec2.sum(spritePos, new Vec2(0.99, 0))));
    	nowQuads.add(getQuad(Vec2.sum(spritePos, new Vec2(0, 0.99))));
    	nowQuads.add(getQuad(Vec2.sum(spritePos, new Vec2(0.99, 0.99))));
    	for (DungeonQuad dnow : nowQuads)
    		nowQuads.add(dnow);
    	
    	// eventi: enter, stay, exit
    	for (DungeonQuad q : nowQuads)
    		if (toQuads.contains(q))
    			q.onPlayerStay(sprite);
    		else
    			q.onPlayerExit(sprite);
    	
    	for (DungeonQuad q : toQuads)
    		if (!nowQuads.contains(q))
    			q.onPlayerEnter(sprite);
    	
		if (Vec2.subtract(sprite.getPosition(), newposition).length() > 0) {
			sprite.setPosition(newposition);
			return true;
		}
		else
			return false;
    }
    
	private void update() 
	{
    	Vec2 chibipos = chibi.getPosition();
    	Vec2 maxchibipos = Vec2.sum(chibi.getPosition(), new Vec2(1, 1));

    	for (Bullet b : bullets) {
        	b.update();
        
        	if (Vec2.isInside(chibipos, maxchibipos, b.getPosition())) {
        		b.onHit(chibi);
        		bullets.remove(b);
        	}
        	else {
        		DungeonQuad q = getQuad(b.getPosition());
        		if (!q.isWalkable())
        			bullets.remove(b);
        	}
        		
		}
		
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
