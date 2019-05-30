package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.GameTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class TextureResource extends Resource
{

	private GameTexture texture;

	public TextureResource(String path)
	{
		super(path);
	}

	@Override
	public GameTexture getContent()
	{
		return texture;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		TextureSlot slot = getTextureSlot();
		texture = new GameTexture(data, path, slot);

		onContentLoaded(texture);
	}

	protected TextureSlot getTextureSlot()
	{
		return TextureSlot.SHARED_DIFFUSE;
	}
	
	protected void resourceCreated(GameTexture creation)
	{
		this.texture = creation;

		onContentLoaded(texture);
	}

	@Override
	void onDispose()
	{
		if(texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}
}
