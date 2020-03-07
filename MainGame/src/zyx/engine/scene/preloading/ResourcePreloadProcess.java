package zyx.engine.scene.preloading;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.scene.loading.LoadingScreenProcess;
import zyx.engine.scene.loading.WaitingProcess;

public class ResourcePreloadProcess extends WaitingProcess implements IResourceReady<Resource>
{
	private String resource;
	private Resource res;
	
	public ResourcePreloadProcess(String resource)
	{
		super(50, "Preloading: " + resource);
		this.resource = resource;
	}

	@Override
	protected void onDoneWaiting()
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
		return super.getTotalProgress() + 1;
	}
}
