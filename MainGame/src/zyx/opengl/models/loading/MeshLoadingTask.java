package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.loading.meshes.ZafLoader;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class MeshLoadingTask extends ScheduledTask<LoadableWorldModelVO>
{
	private ResourceDataInputStream inputData;
	private String id;
	
	public MeshLoadingTask(ITaskCompleted<LoadableWorldModelVO> taskDoneCallback, ResourceDataInputStream data, String id)
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
		
		LoadableWorldModelVO result = ZafLoader.loadMeshFrom(inputData, id);
		taskCompleted(result);
		
		inputData = null;
	}

	@Override
	protected void onCanceled(LoadableWorldModelVO result)
	{
		result.dispose();
	}
}
