package zyx.game.controls.loading.texture;

import java.io.DataInputStream;
import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.textures.AbstractTexture;

public class TextureRequest extends AbstractRequest<AbstractTexture>
{

	private static Object[] PARAMS = new Object[2];

	public TextureRequest(String path, IResourceLoaded callback, AbstractLoader factory)
	{
		super(path, callback, factory);
	}

	@Override
	public void resourceLoaded(DataInputStream data)
	{
		PARAMS[0] = data;
		PARAMS[1] = path;
		
		onLoadComplete(PARAMS);
	}
}
