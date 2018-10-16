package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class MeshResource extends BaseTextureRequiredResource
{

	private LoadableWorldModelVO loadedVo;
	private WorldModel model;

	public MeshResource(String path)
	{
		super(path);
	}

	@Override
	public WorldModel getContent()
	{
		return model;
	}

	@Override
	void onDispose()
	{
		super.onDispose();
		
		if(model != null)
		{
			model.dispose();
			model = null;
		}
		
		if(loadedVo != null)
		{
			loadedVo.dispose();
			loadedVo = null;
		}
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);

		loadTexture(loadedVo.getTexture());
	}

	@Override
	protected void onTextureLoaded(AbstractTexture texture)
	{
		loadedVo.setGameTexture(texture);
		
		model = new WorldModel(loadedVo);
		
		onContentLoaded(model);
	}
}