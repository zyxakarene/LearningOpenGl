package zyx.opengl.shaders.implementations;

public class AmbientOcclusionShader extends BaseBoneShader
{

	private int positionTexUniform;
	private int normalTexUniform;

	public AmbientOcclusionShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void onPostLoading()
	{
		positionTexUniform = UniformUtils.createUniform(program, "gPosition");
		normalTexUniform = UniformUtils.createUniform(program, "gNormal");

		UniformUtils.setUniformInt(positionTexUniform, 0);
		UniformUtils.setUniformInt(normalTexUniform, 1);
	}

	@Override
	public void upload()
	{
	}

	@Override
	protected String getVertexName()
	{
		return "AmbientOcclusionVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "AmbientOcclusionFragment.frag";
	}

	@Override
	public String getName()
	{
		return "AmbientOcclusionShader";
	}
}
