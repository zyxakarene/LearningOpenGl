package zyx.engine.resources.impl;

import java.util.ArrayList;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.*;
import zyx.opengl.models.implementations.renderers.wrappers.AbstractParticleModelWrapper;
import zyx.opengl.models.loading.ParticleLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class ParticleResource extends ExternalResource implements IResourceReady, ITaskCompleted<LoadableParticleVO>
{

	private LoadableParticleVO loadedVo;
	private Resource textureResource;

	private BaseParticleModel model;
	private ArrayList<AbstractParticleModelWrapper> clones;
	private ParticleLoadingTask particleTask;

	public ParticleResource(String path)
	{
		super(path);

		clones = new ArrayList<>();
	}

	@Override
	public AbstractParticleModelWrapper getContent()
	{
		AbstractParticleModelWrapper renderer = model.createWrapper();
		if (renderer.isWorldParticle())
		{
			clones.add(renderer);
		}

		return renderer;
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
		particleTask = new ParticleLoadingTask(this, data, path);
		particleTask.start();
	}

	@Override
	public void onTaskCompleted(LoadableParticleVO data)
	{
		loadedVo = data;
		
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

		if (particleTask != null)
		{
			particleTask.cancel();
			particleTask = null;
		}
		
		particleTask = new ParticleLoadingTask(this, data, path);
		particleTask.start();
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

			onContentLoaded(model);
		}
		else
		{
			for (IParticleModel clone : clones)
			{
				clone.refresh(loadedVo);
			}
			
			if (model.isWorldParticle() == false)
			{
				model.refresh(loadedVo);
			}
		}
	}

	public void removeParticleInstance(AbstractParticleModelWrapper model)
	{
		clones.remove(model);
	}

	@Override
	public String getDebugIcon()
	{
		return "particle.png";
	}
}
