package zyx.game.controls.process;

import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class BaseProcess implements IUpdateable, IDisposeable
{

	private ICallback<BaseProcess> callback;

	public BaseProcess()
	{
	}

	final void start(ICallback<BaseProcess> callback)
	{
		this.callback = callback;
		
		onStart();
	}
	
	protected final void finish()
	{
		onFinish();
		
		if(callback != null)
		{
			callback.onCallback(this);
		}
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	protected void onStart()
	{
		finish();
	}

	protected void onFinish()
	{
	}

	@Override
	public void dispose()
	{
		callback = null;
		
		onDispose();
	}

	protected void onDispose()
	{
	}
}
