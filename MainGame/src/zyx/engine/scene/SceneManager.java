package zyx.engine.scene;

import zyx.engine.scene.loading.LoadingScreenManager;
import zyx.engine.scene.loading.LoadingScreenProcessQueue;
import zyx.game.scene.SceneType;
import zyx.opengl.GLUtils;
import zyx.utils.interfaces.IUpdateable;

public class SceneManager implements IUpdateable
{

	private static final SceneManager INSTANCE = new SceneManager();
	
	private Scene currentScene;
	private SceneType requestedScene;

	private LoadingScreenManager loadingScreen;
	
	public static SceneManager getInstance()
	{
		return INSTANCE;
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
			LoadingScreenProcessQueue queue = currentScene.getLoadingScreenProcess();
			loadingScreen.showLoadingScreenWith(queue, currentScene);
			
			GLUtils.errorCheck();

			requestedScene = null;
		}

		if (loadingScreen != null)
		{
			loadingScreen.update(timestamp, elapsedTime);
		}
		
		if (currentScene != null)
		{
			currentScene.update(timestamp, elapsedTime);
			currentScene.draw();
		}
	}

	public void changeScene(SceneType scene)
	{
		if (loadingScreen == null)
		{
			loadingScreen = LoadingScreenManager.getInstance();
			loadingScreen.initialize();
		}

		requestedScene = scene;
	}
}
