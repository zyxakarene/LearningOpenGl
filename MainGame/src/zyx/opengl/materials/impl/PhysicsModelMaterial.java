package zyx.opengl.materials.impl;

import zyx.opengl.materials.Culling;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.shaders.implementations.Shader;

public class PhysicsModelMaterial extends WorldModelMaterial
{
	public PhysicsModelMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		queue = RenderQueue.OPAQUE;
		castsShadows = false;
		culling = Culling.NONE;
	}

	@Override
	protected Material createInstance()
	{
		return new PhysicsModelMaterial(shaderType);
	}

}
