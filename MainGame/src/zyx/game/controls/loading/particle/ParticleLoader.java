package zyx.game.controls.loading.particle;

import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.models.implementations.WorldParticleModel;

public class ParticleLoader extends AbstractLoader<AbstractInstancedModel>
{
	private static ParticleLoader INSTANCE = new ParticleLoader();
	
	private ParticleLoader()
	{
	}

	public static ParticleLoader getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	protected AbstractInstancedModel createNewInstance(Object[] params)
	{
		LoadableParticleVO vo = (LoadableParticleVO) params[0];
		if (vo.worldParticle)
		{
			return new WorldParticleModel(vo);
		}
		else
		{
			return new ParticleModel(vo);
		}
	}

	@Override
	protected AbstractRequest<AbstractInstancedModel> createRequest(
			String path, 
			IResourceLoaded<AbstractInstancedModel> callback,
			AbstractLoader<AbstractInstancedModel> loader)
	{
		return new ParticleModelRequest(path, callback, this);
	}
}
