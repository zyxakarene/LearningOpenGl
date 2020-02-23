package zyx.opengl.models.loading;

import zyx.opengl.models.loading.particles.ZpfLoader;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.ScheduledTask;

public class ParticleLoadingTask extends ScheduledTask<LoadableParticleVO>
{
	private ResourceDataInputStream inputData;
	private String id;

	public ParticleLoadingTask(ITaskCompleted<LoadableParticleVO> taskDoneCallback, ResourceDataInputStream data, String id)
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
		
		LoadableParticleVO result = ZpfLoader.loadFromZpf(inputData, id);
		taskCompleted(result);
		
		inputData = null;
	}
}