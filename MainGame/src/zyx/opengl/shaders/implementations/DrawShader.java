package zyx.opengl.shaders.implementations;

import zyx.opengl.shaders.AbstractShader;

public class DrawShader extends AbstractShader
{

	public DrawShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
	}

	@Override
	public void upload()
	{
	}

	@Override
	protected String getVertexName()
	{
		return "DrawVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "DrawFragment.frag";
	}

	@Override
	public String getName()
	{
		return "DrawShader";
	}	
}
