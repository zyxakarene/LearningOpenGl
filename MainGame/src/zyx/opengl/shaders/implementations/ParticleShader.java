package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;

public class ParticleShader extends AbstractShader
{

	public static final Matrix4f MATRIX_PROJECTION = WorldShader.MATRIX_PROJECTION;
	public static final Matrix4f MATRIX_VIEW = WorldShader.MATRIX_VIEW;

	public static float elapsedTime = 0;
	
	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int timeTrans;

	public ParticleShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		
		timeTrans = UniformUtils.createUniform(program, "time");
	}

	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformFloat(timeTrans, elapsedTime);
	}

	@Override
	protected String getVertexName()
	{
		return "ParticleVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "ParticleFragment.frag";
	}

	@Override
	public String getName()
	{
		return "ParticleShader";
	}

}
