package zyx.game.controls.models;

import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.textures.GameTexture;

class ModelTextureLoader implements IResourceLoaded<GameTexture>
{

	private final IModelTextureLoaded modelRequest;

	ModelTextureLoader(String path, IModelTextureLoaded modelRequest)
	{
		this.modelRequest = modelRequest;
		
		TextureManager.getInstance().loadTexture("assets/textures/" + path, this);
	}

	@Override
	public void resourceLoaded(GameTexture data)
	{
		modelRequest.onModelTextureLoaded(data);
	}
}
