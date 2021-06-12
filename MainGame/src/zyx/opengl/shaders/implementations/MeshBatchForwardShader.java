package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;

public class MeshBatchForwardShader extends MeshBatchShader implements ILightsShader
{

	private LightShaderObject lightShaderObject;

	//TODO: This actually doesn't draw anything. Plz fix :(
	public MeshBatchForwardShader(Object lock)
	{
		super(lock);
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
		return "MeshBatchForwardVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "WorldForwardFragment.frag";
	}

	@Override
	public String getName()
	{
		return "MeshBatchForwardShader";
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
