package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;

public class UIShader extends AbstractShader
{

	public static final Matrix4f MATRIX_MODEL = new Matrix4f();

	private int modelMatrixTrans;

	public UIShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
	}

	public void upload()
	{
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
	}

	@Override
	protected String getVertexName()
	{
		return "uiVertex.shader";
	}

	@Override
	protected String getFragmentName()
	{
		return "uiFragment.shader";
	}

}
