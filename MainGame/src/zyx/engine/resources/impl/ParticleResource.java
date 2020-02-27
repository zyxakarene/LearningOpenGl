package zyx.engine.resources.impl;

import java.util.ArrayList;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.models.implementations.WorldParticleModel;
import zyx.opengl.models.loading.ParticleLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class ParticleResource extends ExternalResource implements IResourceReady, ITaskCompleted<LoadableParticleVO>
{

	private LoadableParticleVO loadedVo;
	private Resource textureResource;

	private IParticleModel model;
	private ArrayList<IParticleModel> clones;
	private ParticleLoadingTask particleTask;

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
	public String getDebugIcon()
	{
		return "particle.png";
	}
}
