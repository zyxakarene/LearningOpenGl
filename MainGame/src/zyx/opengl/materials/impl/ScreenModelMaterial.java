package zyx.opengl.materials.impl;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.materials.*;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class ScreenModelMaterial extends Material
{
	public Vector3f color;
	public float alpha;
	
	public ScreenModelMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		zTest = ZTest.ALWAYS;
		culling = Culling.NONE;
		blend = BlendMode.ALPHA;

		color = new Vector3f(1f, 1f, 1f);
		alpha = 1f;
		
		stencilMode = StencilMode.MASKING;
		stencilLayer = StencilLayer.PLAYER_CHARACTER;
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
		return new ScreenModelMaterial(shaderType);
	}

}
