package zyx.engine.resources.impl;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class MeshResource extends Resource implements IResourceReady
{

	private LoadableWorldModelVO loadedVo;
	private WorldModel model;
	private Resource textureResource;

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
		model.dispose();
		model = null;
	
		textureResource.unregister(this);
		textureResource = null;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);

		textureResource = ResourceManager.getInstance().getResource(loadedVo.getTexture());
		textureResource.registerAndLoad(this);
	}
	
	@Override
	public void onResourceReady(Resource resource)
	{
		AbstractTexture texture = (AbstractTexture) resource.content;
		loadedVo.setGameTexture(texture);
		
		model = new WorldModel(loadedVo);
		
		onContentLoaded(model);
	}
}