package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.reflections.CubeLoader;
import zyx.opengl.reflections.Cubemap;

public class CubemapResource extends ExternalResource
{

	private Cubemap cubemap;

	public CubemapResource(String path)
	{
		super(path);
	}

	@Override
	public Cubemap getContent()
	{
		return cubemap;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		cubemap = CubeLoader.loadFromCube(data);
		onContentLoaded(cubemap);
	}

	@Override
	protected void onDispose()
	{
		if(cubemap != null)
		{
			cubemap.dispose();
			cubemap = null;
		}
	}
}
