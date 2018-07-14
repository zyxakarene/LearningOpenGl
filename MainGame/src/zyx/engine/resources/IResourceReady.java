package zyx.engine.resources;

import zyx.engine.resources.impl.Resource;

public interface IResourceReady<T extends Resource>
{
	public void onResourceReady(T resource);
}
