package com.y;
import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameLoopThread extends Thread
{
	static final long FPS = 30;
	private GameView view;

	private boolean running = false;

	public GameLoopThread(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime = 0;	// TODO: a 0 non va benissimo
		long sleepTime;
		while (running) {
			Canvas c = null;
			try {
				synchronized (view.getHolder()) {
					c = view.getHolder().lockCanvas();
					if (c != null)
						view.onDraw(c);
				}
			}
			catch (Exception e) { 
				@SuppressWarnings("unused")
				int x = 0;
			}
			finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			} catch (Exception e) {}
		}
	}
}
