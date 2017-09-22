package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;

public class WorldShader extends AbstractShader
{

	public static final Matrix4f MATRIX_PROJECTION = new Matrix4f();
	public static final Matrix4f MATRIX_VIEW = new Matrix4f();
	public static final Matrix4f MATRIX_MODEL = new Matrix4f();

	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int modelMatrixTrans;

	public WorldShader(Object lock)
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
		return "worldVertex.shader";
	}

	@Override
	protected String getFragmentName()
	{
		return "worldFragment.shader";
	}

}
