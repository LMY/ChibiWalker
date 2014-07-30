package com.y.game.shapes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.y.game.core.Vec2;
import com.y.game.scene.Camera;

public class Sprite extends Shape
{
	private int frame = 0;
    private int action = 0;
    private Bitmap image;
    
	public Sprite(Bitmap image, Vec2 size)
	{
		super(size);
		frame = 0;
		action = 0;
		this.image = image;
	}
	
	@Override
	public void draw(Vec2 src, Canvas canvas, Camera camera)
	{
		final int ix = (int) (src.getX()-camera.getScroll().getX());
		final int iy = (int) (src.getY()-camera.getScroll().getY());
		final int ix2 = (int) (ix + size.getX());
		final int iy2 = (int) (iy + size.getY());
		
		final int width = (int) size.getX();
		final int height = (int) size.getY();
		
        int srcX = frame * 64;
        int srcY = action * height;
        Rect srcRect = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(ix, iy, ix2, iy2);
        canvas.drawBitmap(image, srcRect, dst, null);
	}

	@Override
	public void update()
	{
		//TODO: Sprite::update() framenumber
		frame = ++frame % 4; // % sprites[action].length;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		if (this.action != action) {
			this.action = action;
			frame = 0;
		}
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
}
