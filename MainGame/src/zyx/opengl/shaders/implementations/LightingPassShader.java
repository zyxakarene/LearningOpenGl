package zyx.opengl.shaders.implementations;

import zyx.opengl.shaders.AbstractShader;

public class LightingPassShader extends AbstractShader
{

	private int positionTexUniform;
	private int normalTexUniform;
	private int albedoTexUniform;

	public LightingPassShader(Object lock)
	{
		super(lock);
	}

	@Override
	public void upload()
	{
	}

	@Override
	protected void postLoading()
	{
		positionTexUniform = UniformUtils.createUniform(program, "gPosition");
		normalTexUniform = UniformUtils.createUniform(program, "gNormal");
		albedoTexUniform = UniformUtils.createUniform(program, "gAlbedoSpec");
		
		UniformUtils.setUniformInt(positionTexUniform, 0);
		UniformUtils.setUniformInt(normalTexUniform, 1);
		UniformUtils.setUniformInt(albedoTexUniform, 2);
	}

	@Override
	public String getName()
	{
		return "LightingPassShader";
	}

	@Override
	protected String getVertexName()
	{
		return "LightingPassVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "LightingPassFragment.frag";
	}
}
