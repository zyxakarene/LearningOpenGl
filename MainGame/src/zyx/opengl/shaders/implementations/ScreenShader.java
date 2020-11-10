package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.geometry.Rectangle;
import zyx.utils.math.Vector2Int;

public class ScreenShader extends AbstractShader implements ICallback<Vector2Int>
{

	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.UI_ORTHOGRAPHIC_PROJECTION;
	private static final Matrix4f MATRIX_VIEW = new Matrix4f();
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;

	private int modelMatrixTrans;
	private int viewMatrixTrans;
	private int projectionMatrixTrans;
	
	private int ClipXVecTrans;
	private int ClipYVecTrans;

	private Vector2f clipX = new Vector2f(0, ScreenSize.windowWidth);
	private Vector2f clipY = new Vector2f(0, ScreenSize.windowHeight);
	
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
		
		ScreenSize.addListener(this);
		onScreenSizeChanged();
	}

	private void onScreenSizeChanged()
	{
		MATRIX_VIEW.setIdentity();
		MATRIX_VIEW.translate(new Vector2f(-ScreenSize.windowWidth / 2, ScreenSize.windowHeight / 2));
	}
	
	@Override
	public void upload()
	{
		MATRIX_MODEL.m22 = 1f;
		
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		
		UniformUtils.setUniform2F(ClipXVecTrans, clipX.x - 1, clipX.y + 1);
		UniformUtils.setUniform2F(ClipYVecTrans, ScreenSize.windowHeight - clipY.x + 1, ScreenSize.windowHeight - clipY.y - 1);
	}

	public void setClipRect(Rectangle rect)
	{
		clipX.x = rect.x;
		clipX.y = rect.width;
		clipY.x = rect.y;
		clipY.y = rect.height;
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

	@Override
	public void onCallback(Vector2Int data)
	{
		onScreenSizeChanged();
	}
}
