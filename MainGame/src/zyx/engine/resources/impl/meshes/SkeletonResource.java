package zyx.engine.resources.impl.meshes;

import zyx.engine.resources.impl.ExternalResource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.loading.SkeletonLoadingTask;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.TaskScheduler;

public class SkeletonResource extends ExternalResource implements ITaskCompleted<Skeleton>
{

	private Skeleton skeleton;

	public SkeletonResource(String path)
	{
		super(path);
	}

	@Override
	public Skeleton getContent()
	{
		return skeleton;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		SkeletonLoadingTask task = new SkeletonLoadingTask(this, data);
		TaskScheduler.getInstance().addEntry(task);
	}

	@Override
	public void onTaskCompleted(Skeleton data)
	{
		skeleton = data;
		onContentLoaded(skeleton);
	}

	@Override
	protected void onDispose()
	{
		if (skeleton != null)
		{
			skeleton.dispose();
			skeleton = null;
		}
	}

	@Override
	public String getResourceIcon()
	{
		return "skeleton.png";
	}
}
