package com.y.chibiwalker;

import android.content.res.Resources;

import com.y.game.core.Vec2d;
import com.y.game.scene.SceneFactory;

public class SceneDungeonFactory implements SceneFactory
{
	public SceneDungeonFactory() {}
	
	@Override
	public SceneDungeon createScene(Resources resources, Vec2d size)
	{
		return new SceneDungeon(resources, size);
	}
}
