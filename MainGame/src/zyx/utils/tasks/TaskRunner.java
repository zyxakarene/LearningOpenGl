package zyx.utils.tasks;

import zyx.synchronizer.BaseRunner;

class TaskRunner extends BaseRunner<ScheduledTask, ScheduledTask>
{

	@Override
	protected ScheduledTask handleEntry(ScheduledTask task)
	{
		task.run();
		
		return task;
	}
	
	@Override
	protected String getName()
	{
		return "TaskSchedulerRunner";
	}
}
