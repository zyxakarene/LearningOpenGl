package zyx.opengl.textures;

import java.io.InputStream;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.custom.Texture;
import zyx.opengl.textures.enums.TextureFiltering;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.geometry.Rectangle;

public class GameTexture extends AbstractTexture
{

	protected ITexture texture;

	protected GameTexture(ITexture parent, Rectangle rect, String name, TextureSlot textureSlot)
	{
		super(rect, name, textureSlot);

		texture = parent;

		setSizes();
	}

	public GameTexture(InputStream stream, String name, TextureSlot textureSlot)
	{
		this(stream, name, null, textureSlot);
	}

	public GameTexture(InputStream stream, String name, Rectangle rect, TextureSlot textureSlot)
	{
		super(rect, name, textureSlot);
		refresh(stream);
	}

	public void refresh(InputStream stream)
	{
		texture = new Texture(stream, TextureFiltering.NEAREST);
		setSizes();
	}

	protected void setSizes()
	{
		float w = u - x;
		float h = v - y;

		setSizes(texture.getWidth() * w, texture.getHeight() * h);
	}

	@Override
	protected void onBind()
	{
		texture.bind();
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
}
