package zyx.game.controls.loading.particle;

import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.loading.IModelTextureLoaded;
import zyx.game.controls.loading.TextureLoadWrapper;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.particles.loading.ZpfLoader;
import zyx.opengl.textures.AbstractTexture;

public class ParticleModelRequest extends AbstractRequest<IParticleModel> implements IModelTextureLoaded
{
	private static Object[] PARAMS = new Object[1];
	
	private LoadableParticleVO loadedVo;
	private TextureLoadWrapper textureLoader;

	public ParticleModelRequest(String path, IResourceLoaded callback, AbstractLoader factory)
	{
		super(path, callback, factory);
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZpfLoader.loadFromZpf(data);
		textureLoader = new TextureLoadWrapper(loadedVo.getTexture(), this);
	}
	
	@Override
	public void onModelTextureLoaded(AbstractTexture texture)
	{
		PARAMS[0] = loadedVo;
		
		loadedVo.setGameTexture(texture);
		onLoadComplete(PARAMS);
	}

}
