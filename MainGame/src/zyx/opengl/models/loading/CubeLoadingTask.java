package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.loading.cubemaps.CubeLoader;
import zyx.opengl.reflections.LoadableCubemapVO;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class CubeLoadingTask extends ScheduledTask<LoadableCubemapVO>
{
	private ResourceDataInputStream inputData;
	private String id;

	public CubeLoadingTask(ITaskCompleted<LoadableCubemapVO> taskDoneCallback, ResourceDataInputStream data, String id)
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
		
		LoadableCubemapVO result = CubeLoader.loadFromCube(inputData, id);
		taskCompleted(result);
		
		inputData = null;
	}

	@Override
	protected void onCanceled(LoadableCubemapVO result)
	{
		result.dispose();
	}
}
