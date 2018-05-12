package zyx.game.controls.loading.worldmodel;

import java.io.DataInputStream;
import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.loading.IModelTextureLoaded;
import zyx.game.controls.loading.TextureLoadWrapper;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class WorldModelRequest extends AbstractRequest<WorldModel> implements IModelTextureLoaded
{
	private static Object[] PARAMS = new Object[1];

	private LoadableWorldModelVO loadedVo;
	private TextureLoadWrapper textureLoader;

	public WorldModelRequest(String path, IResourceLoaded callback, AbstractLoader factory)
	{
		super(path, callback, factory);
	}

	@Override
	public void resourceLoaded(DataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);
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
