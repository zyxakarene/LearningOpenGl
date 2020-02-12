package zyx.engine.resources.impl.meshes;

import zyx.engine.resources.impl.ExternalResource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.loading.skeletons.SkeletonLoader;

public class SkeletonResource extends ExternalResource
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
		skeleton = SkeletonLoader.loadFromZaf(data);

		onContentLoaded(skeleton);
	}

	@Override
	protected void onDispose()
	{
		if(skeleton != null)
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
