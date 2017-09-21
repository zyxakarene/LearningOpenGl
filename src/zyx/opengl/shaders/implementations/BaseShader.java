package zyx.opengl.shaders.implementations;

import zyx.opengl.shaders.AbstractShader;

public class BaseShader extends AbstractShader
{
	
	public BaseShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{		
	}

	@Override
	protected String getVertexName()
	{
		return "vertex.shader";
	}

	@Override
	protected String getFragmentName()
	{
		return "fragment.shader";
	}

}
