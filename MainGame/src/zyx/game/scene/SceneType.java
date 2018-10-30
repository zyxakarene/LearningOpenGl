package zyx.game.scene;

import zyx.engine.scene.Scene;
import zyx.game.scene.gamescene.GameScene;
import zyx.game.scene.gamescene.StackScene;
import zyx.game.scene.gamescene.TestScene;
import zyx.game.scene.matrix.MatrixScene;
import zyx.game.scene.particle.ParticleScene;

public enum SceneType
{
	
	MATRIX(MatrixScene.class),
	STACK(StackScene.class),
	GAME(GameScene.class),
	TEST(TestScene.class),
	PARTICLE(ParticleScene.class),
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
