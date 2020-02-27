package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.loading.CubeLoadingTask;
import zyx.opengl.reflections.Cubemap;
import zyx.opengl.reflections.LoadableCubemapVO;
import zyx.opengl.textures.CubemapArrayTexture;
import zyx.utils.tasks.ITaskCompleted;

public class CubemapResource extends ExternalResource implements ITaskCompleted<LoadableCubemapVO>
{

	private Cubemap cubemap;
	private CubemapArrayTexture texture;
	private CubeLoadingTask task;

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
		task = new CubeLoadingTask(this, data, path);
		task.start();
	}

	@Override
	public void onTaskCompleted(LoadableCubemapVO data)
	{
		if (texture != null)
		{
			texture.refresh(data);
		}
		else
		{
			texture = new CubemapArrayTexture(data);
			cubemap = new Cubemap(data.positions, texture);
		}

		onContentLoaded(cubemap);

		data.dispose();
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		if (task != null)
		{
			task.cancel();
			task = null;
		}
		
		task = new CubeLoadingTask(this, data, path);
		task.start();
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
		{
			texture.dispose();
			texture = null;
		}

		if (cubemap != null)
		{
			cubemap.dispose();
			cubemap = null;
		}
	}

	@Override
	public String getDebugIcon()
	{
		return "cubemap.png";
	}
}
