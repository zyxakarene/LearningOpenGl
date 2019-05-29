package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.CubemapArrayTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class CubemapResource extends Resource
{

	private CubemapArrayTexture texture;

	public CubemapResource(String path)
	{
		super(path);
	}

	@Override
	public CubemapArrayTexture getContent()
	{
		return texture;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		texture = new CubemapArrayTexture(data, path);

		onContentLoaded(texture);
	}

	protected TextureSlot getTextureSlot()
	{
		return TextureSlot.SLOT_0;
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
