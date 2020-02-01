package zyx.engine.resources.impl;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.models.implementations.WorldParticleModel;
import zyx.opengl.particles.loading.ZpfLoader;
import zyx.opengl.textures.AbstractTexture;

public class ParticleResource extends ExternalResource implements IResourceReady
{

	private LoadableParticleVO loadedVo;
	private IParticleModel model;
	private Resource textureResource;

	public ParticleResource(String path)
	{
		super(path);
	}

	@Override
	public IParticleModel getContent()
	{
		return model;
	}

	@Override
	void onDispose()
	{
		if(model != null)
		{
			model.dispose();
			model = null;
		}
	
		if(textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZpfLoader.loadFromZpf(data);

		textureResource = ResourceManager.getInstance().getResource(loadedVo.getDiffuseTextureId());
		textureResource.registerAndLoad(this);
	}
	
	@Override
	public void onResourceReady(Resource resource)
	{
		AbstractTexture texture = (AbstractTexture) resource.content;
		loadedVo.setDiffuseTexture(texture);
		
		if (loadedVo.worldParticle)
		{
			model = new WorldParticleModel(loadedVo);
		}
		else
		{
			model = new ParticleModel(loadedVo);
		}
		
		onContentLoaded(model);
	}
}