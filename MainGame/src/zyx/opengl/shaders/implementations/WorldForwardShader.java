package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.GLUtils;

public class WorldForwardShader extends WorldShader implements ILightsShader
{
	
	private LightShaderObject lightShaderObject;
	
	public WorldForwardShader(Object lock, int boneCount)
	{
		super(lock, boneCount);
		lightShaderObject = new LightShaderObject();
	}

	@Override
	protected void postLoading()
	{
		super.postLoading();
		lightShaderObject.postLoading(program);
	}
	
	@Override
	public void upload()
	{
		super.upload();
		lightShaderObject.upload();
	}
	
	@Override
	protected String getVertexName()
	{
		return "WorldForwardVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "WorldForwardFragment.frag";
	}

	@Override
	public String getName()
	{
		return "WorldForwardShader_" + GetBoneCount();
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

}
