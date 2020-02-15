package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.loading.skeletons.SkeletonLoader;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class SkeletonLoadingTask extends ScheduledTask<Skeleton>
{

	private ResourceDataInputStream inputData;
	private String id;

	public SkeletonLoadingTask(ITaskCompleted<Skeleton> taskDoneCallback, ResourceDataInputStream data, String id)
	{
		super(taskDoneCallback);
		inputData = data;
		this.id = id;
	}

	@Override
	protected void performTask()
	{
//		try
//		{
//			Thread.sleep((long) (1000 + (10000 * Math.random())));
//		}
//		catch (InterruptedException ex)
//		{
//		}
		
		Skeleton result = SkeletonLoader.loadSkeletonFrom(inputData, id);
		taskCompleted(result);
		
		inputData = null;
	}
}
