package zyx.engine.scene;

import zyx.game.scene.SceneType;
import zyx.utils.interfaces.IUpdateable;

public class SceneManager implements IUpdateable
{

	private static SceneManager instance = new SceneManager();
	private Scene currentScene;
	private SceneType requestedScene;

	public static SceneManager getInstance()
	{
		if (instance == null)
		{
			instance = new SceneManager();
		}

		return instance;
	}

	private SceneManager()
	{
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (requestedScene != null)
		{
			if (currentScene != null)
			{
				currentScene.dispose();
			}

			currentScene = requestedScene.createScene();
			currentScene.initialize();
			
			requestedScene = null;
		}

		if (currentScene != null)
		{
			currentScene.update(timestamp, elapsedTime);
			currentScene.draw();
		}
	}

	public void changeScene(SceneType scene)
	{
		requestedScene = scene;
	}
}
