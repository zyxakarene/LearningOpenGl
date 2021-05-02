package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.AbstractShader;

public class LightingPassShader extends AbstractShader implements ILightsShader
{
	private LightShaderObject lightShaderObject;

	public LightingPassShader(Object lock)
	{
		super(lock);
		lightShaderObject = new LightShaderObject();
	}

	@Override
	public void upload()
	{
		lightShaderObject.upload();
	}

	@Override
	protected void postLoading()
	{
		lightShaderObject.postLoading(program);
	}

	@Override
	public void uploadLightDirection(Vector3f direction)
	{
		bind();
		lightShaderObject.uploadLightDirection(direction);
	}

	@Override
	public void uploadSunMatrix()
	{
		bind();
		lightShaderObject.uploadSunMatrix();
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
