package zyx.game.scene;

import zyx.engine.scene.Scene;
import zyx.game.scene.dev.TestScene;
import zyx.game.scene.dev.ParticleScene;
import zyx.game.scene.game.DinerScene;
import zyx.game.scene.menu.MenuScene;

public enum SceneType
{
	
	MENU,
	GAME,
	
	TEST,
	PARTICLE,
	EMPTY;
	
	public Scene createScene()
	{
		switch (this)
		{
			case MENU: return new MenuScene();
			case GAME: return new DinerScene();
			
			case TEST: return new TestScene();
			case PARTICLE: return new ParticleScene();
			case EMPTY: return new EmptyScene();
		}
		
		throw new RuntimeException("No scene mapped for " + this);
	}
	
}
