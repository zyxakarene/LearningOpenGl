package zyx.opengl.textures.bitmapfont;

import zyx.opengl.materials.impl.ScreenModelMaterial;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class BitmapFont
{
	AbstractTexture texture;
	FontFile fontFile;
	ScreenModelMaterial material;

	BitmapFont(AbstractTexture texture, FontFile fontFile)
	{
		this.texture = texture;
		this.fontFile = fontFile;
		this.material = new ScreenModelMaterial(Shader.SCREEN);
	}

	public void dispose()
	{
		texture = null;
		fontFile = null;
	}
}
