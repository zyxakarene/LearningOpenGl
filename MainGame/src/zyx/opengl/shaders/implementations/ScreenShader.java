package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.GameConstants;

public class ScreenShader extends AbstractShader
{

	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.SHARED_ORTHOGRAPHIC_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;

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

	@Override
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

	@Override
	public String getName()
	{
		return "ScreenShader";
	}
}
