package zyx.opengl.materials.impl;

import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldForwardShader;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

public class ForwardWorldModelMaterial extends WorldModelMaterial
{
	
	public ForwardWorldModelMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		queue = RenderQueue.ALPHA;
		castsShadows = false;
		
		int shadowInt = DepthRenderer.getInstance().depthInt();
		
		textures[TextureSlot.WORLD_SHADOW.index] = new TextureFromInt(64, 64, shadowInt, TextureSlot.WORLD_SHADOW);
	}

	@Override
	public void bind()
	{
		super.bind();
		
		((WorldForwardShader) shader).uploadBones();
	}
	
	@Override
	protected int getTextureCount()
	{
		return 4;
	}

	@Override
	protected Material createInstance()
	{
		return new ForwardWorldModelMaterial(shaderType);
	}

	@Override
	public void setShadowDistance(float distance)
	{
		activeShadowCascades = 0;
	}
}
