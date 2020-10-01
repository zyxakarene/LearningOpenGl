package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.bitmapfont.FontFile;

public class BitmapTextMaterial extends ScreenModelMaterial
{
	public FontFile font;
	
	public BitmapTextMaterial(Shader shader)
	{
		super(shader);
	}
	
	@Override
	protected Material createInstance()
	{
		return new BitmapTextMaterial(shaderType);
	}

}
