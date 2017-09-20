package zyx.opengl.shader.implementations;

import zyx.opengl.shader.AbstractShader;

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
