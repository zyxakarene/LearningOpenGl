package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.SharedShaderObjects;

public class DepthShader extends BaseBoneShader
{

	private static final Matrix4f MATRIX_PROJECTION_VIEW = SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;

	private int projectionViewMatrixTrans;
	private int modelMatrixTrans;

	public DepthShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void onPostLoading()
	{
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		projectionViewMatrixTrans = UniformUtils.createUniform(program, "projectionView");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(projectionViewMatrixTrans, MATRIX_PROJECTION_VIEW);

		//uploadBones();
	}

	@Override
	protected String getVertexName()
	{
		return "DepthVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "DepthFragment.frag";
	}

	@Override
	public String getName()
	{
		return "DepthShader";
	}
	
}
