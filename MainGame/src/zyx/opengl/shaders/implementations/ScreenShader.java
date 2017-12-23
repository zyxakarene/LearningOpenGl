package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.opengl.shaders.AbstractShader;
import zyx.utils.GameConstants;

public class ScreenShader extends AbstractShader
{

	public static final Matrix4f MATRIX_MODEL = new Matrix4f();
	public static final Matrix4f MATRIX_VIEW = new Matrix4f();
	public static final Matrix4f MATRIX_PROJECTION = new Matrix4f();

	private int modelMatrixTrans;
	private int viewMatrixTrans;
	private int projectionMatrixTrans;

	public ScreenShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		
		MATRIX_VIEW.setIdentity();
		MATRIX_VIEW.translate(new Vector2f(-GameConstants.GAME_WIDTH / 2, GameConstants.GAME_HEIGHT / 2));
		
	}

	public void upload()
	{
		MATRIX_MODEL.m22 = 1f;
		
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
	}

	@Override
	protected String getVertexName()
	{
		return "ScreenVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "ScreenFragment.frag";
	}

}
