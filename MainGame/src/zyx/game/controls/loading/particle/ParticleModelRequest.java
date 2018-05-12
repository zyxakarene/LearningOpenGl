package zyx.game.controls.loading.particle;

import java.io.DataInputStream;
import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.loading.IModelTextureLoaded;
import zyx.game.controls.loading.TextureLoadWrapper;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.textures.AbstractTexture;

public class ParticleModelRequest extends AbstractRequest<ParticleModel> implements IModelTextureLoaded
{
	private static Object[] PARAMS = new Object[1];
	
	private LoadableParticleVO loadedVo;
	private TextureLoadWrapper textureLoader;

	public ParticleModelRequest(String path, IResourceLoaded callback, AbstractLoader factory)
	{
		super(path, callback, factory);
	}

	@Override
	public void resourceLoaded(DataInputStream data)
	{
		loadedVo = new LoadableParticleVO("particle.png");
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
