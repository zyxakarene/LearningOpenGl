package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.materials.ZWrite;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class AmbientOcclusionMaterial extends Material
{
	public AmbientOcclusionMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		zWrite = ZWrite.DISABLED;
	}	
		
	public void setPosition(AbstractTexture texture)
	{
		textures[TextureSlot.AO_SCREEN_POSITION.index] = texture;
	}
	
	public void setNormal(AbstractTexture texture)
	{
		textures[TextureSlot.AO_SCREEN_NORMAL.index] = texture;
	}
	
	public void setNoise(AbstractTexture texture)
	{
		textures[TextureSlot.AO_NOISE.index] = texture;
	}
	
	@Override
	protected int getTextureCount()
	{
		return 3;
	}

	@Override
	protected Material createInstance()
	{
		return new AmbientOcclusionMaterial(shaderType);
	}

}
