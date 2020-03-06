package zyx.engine.scene.preloading;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.scene.loading.LoadingScreenProcess;

public class ResourcePreloadProcess extends LoadingScreenProcess implements IResourceReady<Resource>
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
		addDone(1);
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

	@Override
	public int getTotalProgress()
	{
		return 1;
	}
}
