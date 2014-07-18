package com.y;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity
{
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Randomizer.init();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this));
	}
}