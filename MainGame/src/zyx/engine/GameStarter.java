package zyx.engine;

import zyx.engine.scene.Scene;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.scene.SceneType;

public class GameStarter implements Runnable
{

	private final SceneType startScene;

	public GameStarter(SceneType startScene)
	{
		this.startScene = startScene;
	}

	@Override
	public void run()
	{
		ResourceLoader.getInstance().addThreads(1);

		new GameEngine().startWith(startScene);
	}

}
