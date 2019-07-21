package zyx.utils.cheats;

import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadableWorldModelVO vo)
	{
		super(vo);
	}

	@Override
	public void draw()
	{
		shader.bind();
		super.draw();
	}

	@Override
	public void drawShadow()
	{
	}
	
}
