package zyx.utils.tasks;

import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.cheats.Print;

public abstract class ScheduledTask<R>
{

	private ICallback<R> callback;
	private boolean completed;

	private R data;

	public ScheduledTask(ICallback<R> taskDoneCallback)
	{
		callback = taskDoneCallback;
		completed = false;
	}

	final void run()
	{
		performTask();

		if (!completed)
		{
			Print.out("Scheduled task", this, "completed proccessing, but did not report being done!");
			throw new RuntimeException();
		}
	}

	protected abstract void performTask();

	protected final void taskCompleted(R results)
	{
		completed = true;
		data = results;
	}

	void dispatchComplete()
	{
		callback.onCallback(data);
	}

	void dispose()
	{
		callback = null;
		data = null;
	}
}
