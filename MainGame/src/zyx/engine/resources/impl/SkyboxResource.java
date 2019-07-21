package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class SkyboxResource extends BaseTextureRequiredResource
{

	private LoadableWorldModelVO loadedVo;
	private SkyboxModel model;

	public SkyboxResource(String path)
	{
		super(path);
	}

	@Override
	public SkyboxModel getContent()
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
		loadTextures(diffuse);
	}

	@Override
	protected void onTexturesLoaded(AbstractTexture[] texture)
	{
		loadedVo.setDiffuseTexture(texture[0]);
		
		model = new SkyboxModel(loadedVo);
		
		onContentLoaded(model);
	}
}