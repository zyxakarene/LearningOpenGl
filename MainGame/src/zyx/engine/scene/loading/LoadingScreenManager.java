package zyx.engine.scene.loading;

import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.screen.loading.LoadingScreen;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.process.ProcessQueue;
import zyx.utils.interfaces.IUpdateable;

public class LoadingScreenManager implements IUpdateable, ICallback<ProcessQueue>
{
	private static final LoadingScreenManager INSTANCE = new LoadingScreenManager();

	private LoadingScreen screen;
	private LoadingScreenProcessQueue currentQueue;
	private ILoadingScreenDone currentCallback;
	
	public static LoadingScreenManager getInstance()
	{
		return INSTANCE;
	}

	private LoadingScreenManager()
	{
	}

	public void showLoadingScreenWith(LoadingScreenProcessQueue queue, ILoadingScreenDone callback)
	{
		screen.visible = true;
		currentQueue = queue;
		currentCallback = callback;
		
		if (currentQueue.isEmpty())
		{
			onCallback(currentQueue);
		}
		else
		{
			currentQueue.start(this);
		}
	}

	public void initialize()
	{
		screen = new LoadingScreen();
		Stage.instance.loadingScreenLayer.addChild(screen);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (currentQueue != null)
		{
			currentQueue.update(timestamp, elapsedTime);
			
			float progress = currentQueue.getRatioCompleted();
			String description = currentQueue.getCurrentTaskDescription();
			screen.setProgress(progress, description);
		}
	}

	@Override
	public void onCallback(ProcessQueue data)
	{
		screen.visible = false;
		
		if (currentCallback != null)
		{
			currentCallback.onLoadingScreenCompleted();
			currentCallback = null;
		}
	}
}
