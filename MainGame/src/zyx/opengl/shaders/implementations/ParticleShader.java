package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.shaders.AbstractShader;

public class ParticleShader extends AbstractShader
{

	public static final Matrix4f MATRIX_PROJECTION = WorldShader.MATRIX_PROJECTION;
	public static final Matrix4f MATRIX_VIEW = WorldShader.MATRIX_VIEW;
	public static final Matrix4f MATRIX_MODEL = new Matrix4f();

	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int modelMatrixTrans;

	public ParticleShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
	}

	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
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

}
