package com.y.chibiwalker;

import com.y.game.core.GameView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ChibiWalker extends Activity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this, new SceneDungeonFactory()));
	}
}