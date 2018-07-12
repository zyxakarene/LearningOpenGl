package zyx.game.controls.loading;

import zyx.game.controls.loading.texture.TextureLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.textures.AbstractTexture;

public class TextureLoadWrapper implements IResourceLoaded<AbstractTexture>
{

	private final IModelTextureLoaded modelRequest;

	public TextureLoadWrapper(String path, IModelTextureLoaded modelRequest)
	{
		this.modelRequest = modelRequest;
		
		TextureLoader.getInstance().load("assets/textures/" + path, this);
	}

	@Override
	public void resourceLoaded(AbstractTexture data)
	{
		modelRequest.onModelTextureLoaded(data);
	}
}
