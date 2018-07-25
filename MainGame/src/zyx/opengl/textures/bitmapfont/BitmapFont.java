package zyx.opengl.textures.bitmapfont;

import zyx.opengl.textures.AbstractTexture;

public class BitmapFont
{
	AbstractTexture texture;
	FontFile fontFile;

	BitmapFont(AbstractTexture texture, FontFile fontFile)
	{
		this.texture = texture;
		this.fontFile = fontFile;
	}

	public void dispose()
	{
		texture = null;
		fontFile = null;
	}
}
