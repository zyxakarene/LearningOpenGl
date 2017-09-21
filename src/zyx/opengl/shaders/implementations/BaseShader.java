package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;

public class BaseShader extends AbstractShader
{

	private int uniformMatrixTrans;
	
	public static final Matrix4f MATRIX_TRANS = new Matrix4f();
	
	public BaseShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		uniformMatrixTrans = UniformUtils.createUniform(program, "trans");
	}
	
	public void upload()
	{
		UniformUtils.setUniformMatrix(uniformMatrixTrans, MATRIX_TRANS);
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
