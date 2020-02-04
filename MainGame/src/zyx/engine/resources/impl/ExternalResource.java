package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public abstract class ExternalResource extends Resource implements IResourceLoaded<ResourceDataInputStream>
{

	private ResourceRequestDataInput resourceRequest;

	public ExternalResource(String path)
	{
		super(path);
	}

	@Override
	protected void onBeginLoad()
	{
		resourceRequest = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addEntry(resourceRequest);
	}

	@Override
	protected void onDispose()
	{
		if (isLoading() && !isLoaded() && resourceRequest != null)
		{
			ResourceLoader.getInstance().cancelEntry(resourceRequest);
			resourceRequest.dispose();
			resourceRequest = null;
		}
	}
}
