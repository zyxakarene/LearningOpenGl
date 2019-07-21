package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class SkyboxShader extends AbstractShader
{

	private static final Matrix4f MATRIX_VIEW = new Matrix4f();
	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION;
	private static final Matrix4f MATRIX_VIEW_PROJECTION = SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM;

	private int projectionViewMatsUniform;
	private int viewMatrixTrans;
	private int projectionMatrixTrans;

	public SkyboxShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		projectionViewMatsUniform = UniformUtils.createUniform(program, "projView");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		projectionMatrixTrans = UniformUtils.createUniform(program, "proj");
	}
	
	@Override
	public void upload()
	{
		MATRIX_VIEW.load(SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM);
		MATRIX_VIEW.m30 = 0;
		MATRIX_VIEW.m31 = 0;
		MATRIX_VIEW.m32 = 0;
		
		UniformUtils.setUniformMatrix(projectionViewMatsUniform, MATRIX_VIEW_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
	}

	@Override
	protected String getVertexName()
	{
		return "SkyboxVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "SkyboxFragment.frag";
	}

	@Override
	public String getName()
	{
		return "SkyboxShader";
	}
}
