package zyx.engine;

import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.scene.SceneType;
import zyx.net.core.ConnectionEstablisher;
import zyx.net.core.ConnectionHandler;
import zyx.utils.tasks.TaskScheduler;

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
		ConnectionEstablisher.getInstance().connect("80.197.130.80", 8888);
		
		TaskScheduler.getInstance().addThreads(5);
		ResourceLoader.getInstance().addThreads(1);
		ConnectionHandler.getInstance().addThreads(1);

		new GameEngine().startWith(startScene);
	}

}
