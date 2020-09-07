package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.materials.ZTest;
import zyx.opengl.materials.ZWrite;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class DrawMaterial extends Material
{
	public DrawMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
	
		zWrite = ZWrite.DISABLED;
		zTest = ZTest.ALWAYS;
	}
	
	public void setOver(AbstractTexture texture)
	{
		textures[TextureSlot.DRAW_OVERLAY.index] = texture;
	}
	
	public void setUnder(AbstractTexture texture)
	{
		textures[TextureSlot.DRAW_UNDERLAY.index] = texture;
	}
	
	@Override
	protected int getTextureCount()
	{
		return 2;
	}

	@Override
	protected Material createInstance()
	{
		return new DrawMaterial(shaderType);
	}

}
