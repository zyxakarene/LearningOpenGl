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
	
	public static LoadingScreenManager getInstance()
	{
		return INSTANCE;
	}

	private LoadingScreenManager()
	{
	}

	public void showLoadingScreenWith(LoadingScreenProcessQueue queue)
	{
		screen.visible = true;
		currentQueue = queue;
		currentQueue.start(this);
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
			screen.setProgress(progress);
		}
	}

	@Override
	public void onCallback(ProcessQueue data)
	{
	}
}
