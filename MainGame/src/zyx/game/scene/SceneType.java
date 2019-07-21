package zyx.game.scene;

import zyx.engine.scene.Scene;
import zyx.game.scene.game.DragonScene;
import zyx.game.scene.dev.TestScene;
import zyx.game.scene.dev.ParticleScene;
import zyx.game.scene.menu.MenuScene;
import zyx.game.scene.phys.PhysScene;

public enum SceneType
{
	
	MENU(MenuScene.class),
	GAME(DragonScene.class),
	
	TEST(TestScene.class),
	PARTICLE(ParticleScene.class),
	PHYS(PhysScene.class),
	EMPTY(EmptyScene.class);
	
	public final Class<? extends Scene> sceneClass;
	
	private SceneType(Class<? extends Scene> sceneClass)
	{
		this.sceneClass = sceneClass;
	}
	
	public Scene createScene()
	{
		try
		{
			return sceneClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new RuntimeException("Could not create new scene:" + sceneClass.getName());
		}
	}
	
}
