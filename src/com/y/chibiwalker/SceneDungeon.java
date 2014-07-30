package com.y.chibiwalker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.y.chibiwalker.R;
import com.y.game.core.Vec2;
import com.y.game.entities.Particle;
import com.y.game.entities.Tile;
import com.y.game.entities.Character;
import com.y.game.scene.Camera;
import com.y.game.scene.Scene;
import com.y.game.shapes.Rectangle;
import com.y.game.shapes.Sprite;

public class SceneDungeon extends Scene
{
	public final static Vec2 QUADSIZE = new Vec2(64, 64);
	
	private List<Particle> particles;
	private List<Character> players;
	private List<Character> enemies;
	private Tile[][] tiles;
	
	public SceneDungeon(Resources resources, Vec2 viewport)
	{
		super();
		particles = new ArrayList<Particle>();
		players = new ArrayList<Character>();
		enemies = new ArrayList<Character>();
		tiles = null;
		
		setCamera(new Camera(viewport));
		
		initialize(resources);
	}

	private void initialize(Resources resources)
	{
//		int[] res_indexes = { R.drawable.chiarina, R.drawable.bad1,  R.drawable.bad2, R.drawable.bad3, R.drawable.bad4, R.drawable.bad5, R.drawable.bad6, 
//				R.drawable.good1, R.drawable.good2, R.drawable.good3, R.drawable.good4, R.drawable.good5, R.drawable.good6 };

		int SIZEN = 100;
		tiles = new Tile[SIZEN][SIZEN];
		
		final Sprite spriteWall = new Sprite(BitmapFactory.decodeResource(resources, R.drawable.wall), SceneDungeon.QUADSIZE);
		final Rectangle rectBlack = new Rectangle(Color.BLACK, SceneDungeon.QUADSIZE);
		for (int y=0; y<tiles.length; y++)
			for (int x=0; x<tiles[y].length; x++)
			{
				boolean walkable = !(x==0||y==0||x==tiles[y].length||y==tiles.length);
				//walkable &= Randomizer.DEFAULT.randDouble() > 0.4;
				
				if (x > 4 && x < 10 && y > 3 && y < 10)
					walkable = false;
				
				tiles[y][x] = new Tile(new Vec2(x, y), walkable ? rectBlack : spriteWall, this, walkable);
			}
		
		final Sprite spriteChibi = new Sprite(BitmapFactory.decodeResource(resources, R.drawable.chiarina), SceneDungeon.QUADSIZE);
		Chibi chibi = new Chibi(new Vec2(2,2), spriteChibi, this);

		players.add(chibi);
	}


	// TODO: these should return the sub-lists
	public List<Character> getPlayersOnScreen() { return players; }
	public List<Character> getEnemiessOnScreen() { return enemies; }
	public List<Particle> getParticlesOnScreen() { return particles; }
	

	@Override
	public void draw(Canvas canvas)
	{
		final Camera camera = getCamera();
		
		if (tiles != null) {
			final Vec2 scrollVector = camera.getScroll();
			final Vec2 screensize = camera.getViewport();
	        int maxx = 1+(int) Math.ceil(scrollVector.getX() + screensize.getX()); 
	        int maxy = 1+(int) Math.ceil(scrollVector.getY() + screensize.getY());
	        
	        if (maxy >= tiles.length)
	        	maxy = tiles.length;
	        if (maxx >= tiles[0].length)
	        	maxx = tiles[0].length;
			
	        for (int y=(int)scrollVector.getY();y<maxy; y++)
	        	for (int x=(int)scrollVector.getX(); x<maxx; x++)
	        		tiles[y][x].draw(canvas, camera);
		}
		
		for (Character c : enemies)
			c.draw(canvas, camera);
		for (Character c : players)
			c.draw(canvas, camera);
		for (Particle p : particles)
			p.draw(canvas, camera);			
	}

    private Tile getTile(Vec2 pos)
    {
//    	int px = (int)pos.getX();
//    	int py = (int)pos.getY();
//    	
//    	if (tiles == null || py < 0 || py >= tiles.length || tiles[py] == null || px < 0 || px >= tiles[py].length)
//    		return null;
//    	else
//    		return tiles[py][px];
    	
    	try { return tiles[(int)pos.getY()][(int)pos.getX()]; }
    	catch (Exception e) { return null; }
    }
    
    private Set<Tile> getTiles(Vec2 pos)
    {
    	Set<Tile> Quads = new HashSet<Tile>();
    	Quads.add(getTile(pos));
    	Quads.add(getTile(Vec2.sum(pos, new Vec2(0.99, 0))));
    	Quads.add(getTile(Vec2.sum(pos, new Vec2(0, 0.99))));
    	Quads.add(getTile(Vec2.sum(pos, new Vec2(0.99, 0.99))));
    	return Quads;
    }
    
    
    public boolean isPlayer(Character sprite) { return players.contains(sprite); }
    public boolean isEnemy(Character sprite) { return enemies.contains(sprite); }
    
    public boolean move(Character sprite, Vec2 newposition)
    {
    	return isPlayer(sprite) ? movePlayer(sprite, newposition) : moveEnemy(sprite, newposition);
    }
    		
    public boolean movePlayer(Character sprite, Vec2 newposition)
    {
    	// quadrati destinazione: se non è movimento in un quadrato vuoto, abort
    	final Set<Tile> toQuads = getTiles(newposition);
    	for (Tile q : toQuads)
    		if (q == null || !q.isWalkable())
        		return false;
    	
    	// quadrati attualmente occupati
    	final Vec2 spritePos = sprite.getPosition();
    	
    	// eventi: enter, stay, exit
    	final Set<Tile> nowQuads = getTiles(spritePos);
    	for (Tile q : nowQuads)
    		if (toQuads.contains(q))
    			q.onPlayerStay(sprite);
    		else
    			q.onPlayerExit(sprite);
    	
    	for (Tile q : toQuads)
    		if (!nowQuads.contains(q))
    			q.onPlayerEnter(sprite);
    	
		if (spritePos != newposition) { //Vec2.subtract(spritePos, newposition).length() > 0) {
			sprite.setPosition(newposition);
			return true;
		}
		else
			return false;
    }
    
    public boolean moveEnemy(Character sprite, Vec2 newposition)
    {
    	// quadrati destinazione: se non è movimento in un quadrato vuoto, abort
    	final Set<Tile> toQuads = getTiles(newposition);
    	for (Tile q : toQuads)
    		if (q == null || !q.isWalkable())
        		return false;
    	
    	// quadrati attualmente occupati
    	final Vec2 spritePos = sprite.getPosition();
    	
    	// eventi: enter, stay, exit
    	final Set<Tile> nowQuads = getTiles(spritePos);
    	for (Tile q : nowQuads)
    		if (toQuads.contains(q))
    			q.onEnemyStay(sprite);
    		else
    			q.onEnemyExit(sprite);
    	
    	for (Tile q : toQuads)
    		if (!nowQuads.contains(q))
    			q.onEnemyEnter(sprite);
    	
		if (spritePos != newposition) { //Vec2.subtract(spritePos, newposition).length() > 0) {
			sprite.setPosition(newposition);
			return true;
		}
		else
			return false;
    }
    
	@Override
	public void update()
	{
		for (Character c : players)
			c.update();
		
		for (Particle p : particles) {
			p.update();
			if (p.isExpired())
				particles.remove(p);
			
			final List<Character> playersOnScreen = getPlayersOnScreen();
			for (Character player : playersOnScreen) {
		    	Vec2 chibipos = player.getPosition();
		    	Vec2 maxchibipos = Vec2.sum(player.getPosition(), new Vec2(1, 1));
		    	
	        	if (Vec2.isInside(chibipos, maxchibipos, p.getPosition())) {
	        		p.hit(player);
	        		particles.remove(p);
	        	}
	        	else {
	        		Tile q = getTile(p.getPosition());
	        		if (q == null || !q.isWalkable())
	        			particles.remove(p);
	        	}
			}
		}
		for (Character c : enemies)
			c.update();

		if (tiles != null)
			for (int y=0; y<tiles.length; y++)
				for (int x=0; x<tiles[y].length; x++)
					tiles[y][x].update();

		getCamera().update();	// does nothing
	}

	@Override
	public void onTouchEvent(float x, float y)
	{
		Chibi chibi = (Chibi) this.players.get(0);
		chibi.RequestGo((int) x, (int) y);
	}
	    
	@Override
	public Scene nextScene() {
		return this;	// don't change scene
	}
}
