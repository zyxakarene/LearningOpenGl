package zyx.engine.resources;

import zyx.engine.resources.impl.Resource;

public interface IResourceReloaded<T extends Resource>
{
	public void onResourceReloaded(T resource);
}
