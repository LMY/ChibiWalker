package com.y.game.core;

import com.y.game.scene.Scene;
import com.y.game.scene.SceneFactory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
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
	public GameView(Context context, final SceneFactory scenecreator)
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
				int width = getWidth();
				int height = getHeight();
				Resources resources = getResources();
				
				scene = scenecreator.createScene(resources, new Vec2d(width, height));
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
			}
		});
	}
	
	private Scene scene;

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);

		scene = scene.nextScene();
		scene.update();
		scene.draw(canvas);
	}

	private long lastClick = -1;
	
    @SuppressLint("ClickableViewAccessibility") // porcaputtana non so come risolvere questo
	@Override
    public boolean onTouchEvent(MotionEvent event)
    {
          if (lastClick < 0 || System.currentTimeMillis() - lastClick > 5) {
                 lastClick = System.currentTimeMillis();
                 synchronized (getHolder()) {
                	 scene.onTouchEvent(event.getX(), event.getY());
                 }
          }

          return true;
    }
    
    @Override
    public boolean performClick()
    {
    	super.performClick();
        return true;
    }
    
}

