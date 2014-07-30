package com.y.game.scene;

import android.content.res.Resources;

import com.y.chibiwalker.SceneDungeon;
import com.y.game.core.Vec2d;

public interface SceneFactory
{
	public SceneDungeon createScene(Resources resources, Vec2d size);
}
