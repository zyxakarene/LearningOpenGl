package zyx.game.controls.loading.particle;

import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;

public class ParticleLoader extends AbstractLoader<ParticleModel>
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
	protected ParticleModel createNewInstance(Object[] params)
	{
		LoadableParticleVO vo = (LoadableParticleVO) params[0];
		return new ParticleModel(vo);
	}

	@Override
	protected AbstractRequest<ParticleModel> createRequest(String path, IResourceLoaded<ParticleModel> callback, AbstractLoader<ParticleModel> loader)
	{
		return new ParticleModelRequest(path, callback, this);
	}
}
