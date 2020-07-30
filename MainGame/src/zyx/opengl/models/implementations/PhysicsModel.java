package zyx.opengl.models.implementations;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadableWorldModelVO vo)
	{
		super(vo);
	}
	
	@Override
	public void draw()
	{
		meshShader.bind();
		meshShader.upload();
		
		super.draw();
	}

	@Override
	public void drawShadow()
	{
	}
}
