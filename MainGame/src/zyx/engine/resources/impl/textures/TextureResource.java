package zyx.engine.resources.impl.textures;

import zyx.engine.resources.impl.ExternalResource;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.GameTexture;
import zyx.opengl.textures.MissingTexture;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.cheats.Print;

public class TextureResource extends ExternalResource
{

	private AbstractTexture texture;

	public TextureResource(String path)
	{
		super(path);
	}

	@Override
	public AbstractTexture getContent()
	{
		return texture;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		Print.out("Creating texture data for", path);
		
		TextureSlot slot = getTextureSlot();
		texture = new GameTexture(data, path, slot);

		onContentLoaded(texture);
	}

	@Override
	public void onResourceFailed(String path)
	{
		TextureSlot slot = getTextureSlot();
		texture = MissingTexture.getAsSlot(slot);
		
		onContentLoaded(texture);
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		if (texture instanceof GameTexture)
		{
			((GameTexture) texture).refresh(data);
		}
	}

	protected TextureSlot getTextureSlot()
	{
		return TextureSlot.SHARED_DIFFUSE;
	}

	protected void resourceCreated(AbstractTexture creation)
	{
		this.texture = creation;

		onContentLoaded(texture);
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
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
