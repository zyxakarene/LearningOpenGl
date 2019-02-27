package zyx.utils.tasks;

import zyx.synchronizer.BaseAsyncSynchronizer;

public class TaskScheduler extends BaseAsyncSynchronizer<ScheduledTask, ScheduledTask>
{

	private static final TaskScheduler INSTANCE = new TaskScheduler();

	public static TaskScheduler getInstance()
	{
		return INSTANCE;
	}

	private TaskExchange exchange;
	
	public TaskScheduler()
	{
		exchange = new TaskExchange();
		setExchange(exchange);
	}	

	@Override
	public void addThreads(int count)
	{
		for (int i = 0; i < count; i++)
		{
			TaskRunner runner = new TaskRunner();
			addRunner(runner);
		}
	}
}
