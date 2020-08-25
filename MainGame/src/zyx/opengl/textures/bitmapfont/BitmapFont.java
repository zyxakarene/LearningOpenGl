package zyx.opengl.textures.bitmapfont;

import zyx.opengl.materials.impl.ScreenModelMaterial;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class BitmapFont
{
	FontFile fontFile;
	ScreenModelMaterial material;

	BitmapFont(AbstractTexture texture, FontFile fontFile)
	{
		this.fontFile = fontFile;
		
		material = new ScreenModelMaterial(Shader.SCREEN);
		material.setDiffuse(texture);
	}

	public void dispose()
	{
		fontFile = null;
	}
}
