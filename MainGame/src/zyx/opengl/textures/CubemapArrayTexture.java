package zyx.opengl.textures;

import java.io.InputStream;
import zyx.opengl.textures.custom.CubeTexture3D;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureSlot;

public class CubemapArrayTexture extends AbstractTexture
{

	protected ITexture texture;

	public CubemapArrayTexture(InputStream stream, String name)
	{
		super(name, TextureSlot.SLOT_10);

		
		texture = new CubeTexture3D(stream);

		setSizes(texture.getWidth(), texture.getHeight());
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
