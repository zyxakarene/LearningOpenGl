package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.renderers.PhysicsModelRenderer;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadablePhysicsModelVO vo)
	{
		super(vo);
	}
	
	@Override
	public void draw(WorldModelMaterial material)
	{
		meshShader.bind();
		meshShader.upload();
		
		super.draw(material);
	}

	@Override
	public PhysicsModelRenderer createRenderer()
	{
		return new PhysicsModelRenderer(this, defaultMaterial);
	}
	
	
}
