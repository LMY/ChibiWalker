package com.y;

import com.y.chibiwalker.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView
{
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;

	@SuppressLint("NewApi")
	public GameView(Context context)
	{
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback()
		{
			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				boolean retry = true;
				gameLoopThread.setRunning(false);

				while (retry)
				{
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
			}
		});
	}
	
	private Dungeon dungeon;

	private void createSprites() 
	{
		int[] res_indexes = { R.drawable.chiarina, R.drawable.bad1,  R.drawable.bad2, R.drawable.bad3, R.drawable.bad4, R.drawable.bad5, R.drawable.bad6, 
							R.drawable.good1,R.drawable.good2, R.drawable.good3, R.drawable.good4, R.drawable.good5, R.drawable.good6 };

		Resources resources = getResources();
		Bitmap[] bmps = new Bitmap[res_indexes.length];
		for (int i=0; i<bmps.length; i++)
			bmps[i] = BitmapFactory.decodeResource(resources, res_indexes[i]);
		
		// create sprites
		final int INITIAL_SPRITES = 0;
		
		Sprite chibi = new Sprite(this, bmps[0], Sprite.Action.IDLE);
		chibi.x = 0;
		chibi.y = 0;

		for (int i=0; i<INITIAL_SPRITES; i++)
		{
			int rnd = Randomizer.instance.randInt(1, bmps.length-1);
			dungeon.addSprite(new Sprite(this, bmps[rnd]));
		}
		
		Bitmap dungeonwall = BitmapFactory.decodeResource(resources, R.drawable.wall);
		
		int width = getWidth();
		int height = getHeight();
		dungeon = new Dungeon(chibi, dungeonwall, width, height);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		dungeon.draw(canvas);
	}

	private long lastClick = -1;
	
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
          if (lastClick < 0 || System.currentTimeMillis() - lastClick > 200) {
                 lastClick = System.currentTimeMillis();
                 synchronized (getHolder()) {
                	 if (dungeon.isScreenEmpty((int) event.getX(), (int) event.getY()))
                		 dungeon.RequestPlayerGo(event.getX(), event.getY());
                 }
          }

          return true;
    }
}
