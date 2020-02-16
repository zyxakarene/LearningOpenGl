package zyx.engine.resources.impl;

import java.util.ArrayList;
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
	private Resource textureResource;

	private IParticleModel model;
	private ArrayList<IParticleModel> clones;

	public ParticleResource(String path)
	{
		super(path);

		clones = new ArrayList<>();
	}

	@Override
	public IParticleModel getContent()
	{
		IParticleModel particleModel = model;

		if (model != null && model.isWorldParticle())
		{
			particleModel = model.cloneParticle();
			clones.add(particleModel);
		}

		return particleModel;
	}

	@Override
	protected void onDispose()
	{
		if (model != null)
		{
			model.dispose();
			model = null;
		}

		if (textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}

		for (IParticleModel clone : clones)
		{
			clone.dispose();
		}
		clones.clear();
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZpfLoader.loadFromZpf(data);

		textureResource = ResourceManager.getInstance().getResource(loadedVo.getDiffuseTextureId());
		textureResource.registerAndLoad(this);
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		if (textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}

		loadedVo = ZpfLoader.loadFromZpf(data);

		textureResource = ResourceManager.getInstance().getResource(loadedVo.getDiffuseTextureId());
		textureResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(Resource resource)
	{
		AbstractTexture texture = (AbstractTexture) resource.content;
		loadedVo.setDiffuseTexture(texture);

		if (model == null)
		{
			if (loadedVo.worldParticle)
			{
				model = new WorldParticleModel(loadedVo);
			}
			else
			{
				model = new ParticleModel(loadedVo);
			}

			clones.add(model);

			onContentLoaded(model);
		}
		else
		{
			for (IParticleModel clone : clones)
			{
				clone.refresh(loadedVo);
			}
		}
	}

	public void removeParticleInstance(IParticleModel model)
	{
		clones.remove(model);
	}

	@Override
	public String getResourceIcon()
	{
		return "particle.png";
	}
}
