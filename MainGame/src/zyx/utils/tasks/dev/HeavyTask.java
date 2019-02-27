package zyx.utils.tasks.dev;

import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.FloatMath;
import zyx.utils.tasks.ScheduledTask;

public class HeavyTask extends ScheduledTask<Float>
{

	public HeavyTask(ICallback<Float> taskDoneCallback)
	{
		super(taskDoneCallback);
	}

	@Override
	protected void performTask()
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException ex)
		{
		}
		
		taskCompleted(FloatMath.PI);
	}

}
