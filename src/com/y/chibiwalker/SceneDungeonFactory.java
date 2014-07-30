package com.y.chibiwalker;

import android.content.res.Resources;

import com.y.game.core.Vec2;
import com.y.game.scene.SceneFactory;

public class SceneDungeonFactory implements SceneFactory
{
	public SceneDungeonFactory() {}
	
	@Override
	public SceneDungeon createScene(Resources resources, Vec2 size)
	{
		return new SceneDungeon(resources, size);
	}
}
