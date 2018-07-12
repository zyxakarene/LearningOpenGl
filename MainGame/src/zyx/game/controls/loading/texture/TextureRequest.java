package zyx.game.controls.loading.texture;

import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;

public class TextureRequest extends AbstractRequest<AbstractTexture>
{

	private static Object[] PARAMS = new Object[2];

	public TextureRequest(String path, IResourceLoaded callback, AbstractLoader factory)
	{
		super(path, callback, factory);
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		PARAMS[0] = data;
		PARAMS[1] = path;
		
		onLoadComplete(PARAMS);
	}
}
