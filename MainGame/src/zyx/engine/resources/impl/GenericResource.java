package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class GenericResource extends ExternalResource
{

	public GenericResource(String path)
	{
		super(path);
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		onContentLoaded(data);
	}
}
