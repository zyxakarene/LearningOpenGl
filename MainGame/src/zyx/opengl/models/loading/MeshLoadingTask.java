package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.loading.meshes.ZafLoader;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class MeshLoadingTask extends ScheduledTask<LoadableWorldModelVO>
{

	private ResourceDataInputStream inputData;

	public MeshLoadingTask(ITaskCompleted<LoadableWorldModelVO> taskDoneCallback, ResourceDataInputStream data)
	{
		super(taskDoneCallback);
		inputData = data;
	}

	@Override
	protected void performTask()
	{
		LoadableWorldModelVO result = ZafLoader.loadMeshFrom(inputData);
		taskCompleted(result);
		
		inputData = null;
	}
}
