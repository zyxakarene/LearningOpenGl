package zyx.opengl.models.implementations;

import zyx.opengl.materials.Material;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadableWorldModelVO vo)
	{
		super(vo);
	}
	
	@Override
	public void draw(Material material)
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
