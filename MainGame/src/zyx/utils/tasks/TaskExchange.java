package zyx.utils.tasks;

import zyx.synchronizer.BaseExchange;

class TaskExchange extends BaseExchange<ScheduledTask, ScheduledTask>
{

	@Override
	protected void onReplyCompleted(ScheduledTask task)
	{
		task.dispatchComplete();
		task.dispose();
	}
}
