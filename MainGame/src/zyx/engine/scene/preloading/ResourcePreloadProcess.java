package zyx.engine.scene.preloading;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.game.controls.process.BaseProcess;

public class ResourcePreloadProcess extends BaseProcess implements IResourceReady<Resource>
{
	private String resource;
	private Resource res;
	
	public ResourcePreloadProcess(String resource)
	{
		this.resource = resource;
	}

	@Override
	protected void onStart()
	{
		res = ResourceManager.getInstance().getResource(resource);
		res.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(Resource resource)
	{
		finish();
	}

	@Override
	protected void onDispose()
	{
		if (res != null)
		{
			res.unregister(this);
			res = null;
		}
	}
}
