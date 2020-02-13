package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.loading.skeletons.SkeletonLoader;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class SkeletonLoadingTask extends ScheduledTask<Skeleton>
{

	private ResourceDataInputStream inputData;

	public SkeletonLoadingTask(ITaskCompleted<Skeleton> taskDoneCallback, ResourceDataInputStream data)
	{
		super(taskDoneCallback);
		inputData = data;
	}

	@Override
	protected void performTask()
	{
		Skeleton result = SkeletonLoader.loadSkeletonFrom(inputData);
		taskCompleted(result);
		
		inputData = null;
	}
}
