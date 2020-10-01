package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.materials.ZTest;
import zyx.opengl.materials.ZWrite;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class DeferredMaterial extends Material
{
	public DeferredMaterial(Shader shader)
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
		
	public void setAmbientOcclusion(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_AO.index] = texture;
	}
	
	public void setColorSpec(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_COLOR_SPEC.index] = texture;
	}
	
	public void setCubeIndex(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_CUBE_INDEX.index] = texture;
	}
	
	public void setDepth(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_DEPTH.index] = texture;
	}
	
	public void setNormal(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_NORMAL.index] = texture;
	}
	
	public void setPosition(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_POSITION.index] = texture;
	}
	
	public void setShadow(AbstractTexture texture)
	{
		textures[TextureSlot.DEFERRED_SHADOW.index] = texture;
	}
	
	@Override
	protected int getTextureCount()
	{
		return 7;
	}

	@Override
	protected Material createInstance()
	{
		return new DeferredMaterial(shaderType);
	}

}
