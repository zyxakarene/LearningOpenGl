package zyx.game.controls.loading.texture;

import java.io.InputStream;
import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.GameTexture;

public class TextureLoader extends AbstractLoader<AbstractTexture>
{
	private static TextureLoader INSTANCE = new TextureLoader();
	
	private TextureLoader()
	{
	}

	public static TextureLoader getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	protected AbstractTexture createNewInstance(Object[] params)
	{
		InputStream data = (InputStream) params[0];
		String path = (String) params[1];
		
		return new GameTexture(data, path);
	}

	@Override
	protected AbstractRequest<AbstractTexture> createRequest(String path, IResourceLoaded<AbstractTexture> callback, AbstractLoader<AbstractTexture> loader)
	{
		return new TextureRequest(path, callback, this);
	}
}
