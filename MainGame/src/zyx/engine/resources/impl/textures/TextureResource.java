package zyx.engine.resources.impl.textures;

import zyx.engine.resources.impl.ExternalResource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.GameTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class TextureResource extends ExternalResource
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

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		if (texture != null)
		{
			texture.refresh(data);
		}
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
	protected void onDispose()
	{
		if(texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}
	
	@Override
	public String getResourceIcon()
	{
		return "texture.png";
	}
}
