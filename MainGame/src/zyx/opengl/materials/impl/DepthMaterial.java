package zyx.opengl.materials.impl;

import zyx.opengl.materials.*;
import zyx.opengl.shaders.implementations.Shader;

public class DepthMaterial extends WorldModelMaterial
{

	public DepthMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		zWrite = ZWrite.ENABLED;
		zTest = ZTest.LESS;
		culling = Culling.BACK;
		blend = BlendMode.NORMAL;
		queue = RenderQueue.OPAQUE;

		stencilMode = StencilMode.NOTHING;
		stencilLayer = StencilLayer.NOTHING;
	}

	@Override
	protected int getTextureCount()
	{
		return 0;
	}

	@Override
	protected Material createInstance()
	{
		return new DepthMaterial(shaderType);
	}

}
