package zyx.opengl.materials.impl;

import zyx.opengl.materials.*;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class ParticleModelMaterial extends Material
{
	public ParticleModelMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		zWrite = ZWrite.DISABLED;
		zTest = ZTest.LESS;
		culling = Culling.NONE;
		blend = BlendMode.PARTICLES;
		priority = MaterialPriority.TRANSPARENT_MIN;
		
		stencilMode = StencilMode.NOTHING;
		stencilLayer = StencilLayer.NOTHING;
	}
	
	public AbstractTexture getDiffuse()
	{
		return textures[TextureSlot.SHARED_DIFFUSE.index];
	}
	
	public void setDiffuse(AbstractTexture texture)
	{
		textures[TextureSlot.SHARED_DIFFUSE.index] = texture;
	}
	
	@Override
	protected int getTextureCount()
	{
		return 1;
	}

	@Override
	protected Material createInstance()
	{
		return new ParticleModelMaterial(shaderType);
	}

}
