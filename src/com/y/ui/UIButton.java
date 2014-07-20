package com.y.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.y.GameView;
import com.y.dungeon.Sprite;
import com.y.dungeon.Vec2;

public abstract class UIButton extends Sprite
{
	private Sprite player;
	
	public UIButton(GameView gameView, Bitmap bmp, Sprite player) {
		super(gameView, bmp);
		this.player = player;
	}
	
	@Override
    public void draw(Canvas canvas, Vec2 scrollVector)
    {
        int ix = (int) position.getX();
        int iy = (int) position.getY();
        
        int srcX = currentFrame * width;
        int srcY = 0 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix + (int)(width*SCALE), iy + (int)(height*SCALE));
        canvas.drawBitmap(bmp, src, dst, null);
    }
	
	public abstract void onTouchEvent(int x, int y);
	
	
	public static UIButton buttonUp = null;
	public static UIButton buttonDown = null;
	public static UIButton buttonLeft = null;
	public static UIButton buttonRight = null;
	public static UIButton buttonAttack = null;
	public static void initButtons(GameView gameView, final Sprite player, Bitmap bmpUp, Bitmap bmpDown, Bitmap bmpRight, Bitmap bmpLeft, Bitmap bmpAttack)
	{
		buttonUp = new UIButton(gameView, bmpUp, player) {
			@Override
			public void onTouchEvent(int x, int y) {
				player.RequestGo((int)player.getPosition().getX(), (int)player.getPosition().getY()-1);
			}
		};
		
		buttonDown = new UIButton(gameView, bmpDown, player) {
			@Override
			public void onTouchEvent(int x, int y) {
				player.RequestGo((int)player.getPosition().getX(), (int)player.getPosition().getY()+1);
			}
		};
		
		buttonLeft = new UIButton(gameView, bmpDown, player) {
			@Override
			public void onTouchEvent(int x, int y) {
				player.RequestGo((int)player.getPosition().getX()-1, (int)player.getPosition().getY());
			}
		};
		buttonRight = new UIButton(gameView, bmpDown, player) {
			@Override
			public void onTouchEvent(int x, int y) {
				player.RequestGo((int)player.getPosition().getX()+1, (int)player.getPosition().getY());
			}
		};
		
		buttonAttack = new UIButton(gameView, bmpAttack, player) {
			@Override
			public void onTouchEvent(int x, int y) {
				player.Attack((int)player.getPosition().getX()+1, (int)player.getPosition().getY());
			}
		};
	}
	
	
}
