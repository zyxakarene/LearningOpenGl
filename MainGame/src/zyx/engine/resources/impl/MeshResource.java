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

		String diffuse = loadedVo.getDiffuseTextureId();
		String normal = loadedVo.getNormalTextureId();
		String specular = loadedVo.getSpecularTextureId();
		loadTextures(diffuse, normal, specular);
	}

	@Override
	protected void onTexturesLoaded(AbstractTexture[] texture)
	{
		loadedVo.setDiffuseTexture(texture[0]);
		loadedVo.setNormalTexture(texture[1]);
		loadedVo.setSpecularTexture(texture[2]);
		
		model = new WorldModel(loadedVo);
		
		onContentLoaded(model);
	}
}