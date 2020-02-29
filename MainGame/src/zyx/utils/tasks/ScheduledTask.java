package zyx.utils.tasks;

import zyx.utils.cheats.Print;

public abstract class ScheduledTask<R>
{

	private ITaskCompleted<R> callback;
	private boolean completed;
	private boolean canceled;

	private R data;

	public ScheduledTask(ITaskCompleted<R> taskDoneCallback)
	{
		callback = taskDoneCallback;
		completed = false;
	}

	final void run()
	{
		performTask();

		if (!completed)
		{
			Print.err("Scheduled task", this, "completed proccessing, but did not report being done!");
			throw new RuntimeException();
		}
	}

	public final void start()
	{
		TaskScheduler.getInstance().addEntry(this);
	}
	
	protected abstract void performTask();

	protected final void taskCompleted(R results)
	{
		completed = true;
		data = results;
	}
	
	public final void cancel()
	{
		canceled = true;
	}
	
	public boolean isCanceled()
	{
		return canceled;
	}
	
	void dispatchComplete()
	{
		if (canceled)
		{
			onCanceled(data);
		}
		else
		{
			callback.onTaskCompleted(data);
		}
	}

	void dispose()
	{
		callback = null;
		data = null;
	}

	protected void onCanceled(R result)
	{
	}
}
