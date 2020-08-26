package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadableWorldModelVO vo)
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
	public void drawShadow()
	{
	}
}
