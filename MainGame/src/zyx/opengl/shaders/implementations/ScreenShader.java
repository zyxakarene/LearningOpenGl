package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.geometry.Rectangle;

public class ScreenShader extends AbstractShader
{

	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.SHARED_ORTHOGRAPHIC_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW = new Matrix4f();
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;

	private int modelMatrixTrans;
	private int viewMatrixTrans;
	private int projectionMatrixTrans;
	
	private int ClipXVecTrans;
	private int ClipYVecTrans;

	private Vector2f clipX = new Vector2f(0, GameConstants.GAME_WIDTH);
	private Vector2f clipY = new Vector2f(0, GameConstants.GAME_HEIGHT);
	
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
		ClipXVecTrans = UniformUtils.createUniform(program, "clipX");
		ClipYVecTrans = UniformUtils.createUniform(program, "clipY");
		
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
		
		UniformUtils.setUniform2F(ClipXVecTrans, clipX.x - 1, clipX.y + 1);
		UniformUtils.setUniform2F(ClipYVecTrans, GameConstants.GAME_HEIGHT - clipY.x + 1, GameConstants.GAME_HEIGHT - clipY.y - 1);
	}

	public void setClipRect(float left, float right, float upper, float lower)
	{
		clipX.x = left;
		clipX.y = right;
		clipY.x = upper;
		clipY.y = lower;
	}
	
	public void getClipRect(Rectangle out)
	{
		out.x = clipX.x;
		out.y = clipY.x;
		out.width = clipX.y;
		out.height = clipY.y;
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
