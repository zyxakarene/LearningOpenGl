package zyx.opengl.textures.bitmapfont;

import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;

public class BitmapFont implements IDisposeable
{
	public FontFile fontFile;
	public AbstractTexture texture;

	BitmapFont(AbstractTexture texture, FontFile fontFile)
	{
		this.fontFile = fontFile;
		this.texture = texture;
	}

	@Override
	public void dispose()
	{
		texture = null;
		fontFile = null;
	}
}
