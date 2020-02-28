package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class MeshBatchShader extends AbstractShader
{

	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_PROJECTION_VIEW = SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW_INVERT_TRANSPOSE = new Matrix4f();

	private int ViewMatrixTrans;
	private int projectionViewMatrixTrans;
	private int cubemapColor;

	private int viewMatrixTrans_InverseTranspose;

	public MeshBatchShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		cubemapColor = UniformUtils.createUniform(program, "cubemapColor");
		
		projectionViewMatrixTrans = UniformUtils.createUniform(program, "projectionView");
		ViewMatrixTrans = UniformUtils.createUniform(program, "view");

		viewMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "viewInverseTranspose");
		
		UniformUtils.setUniformFloat(cubemapColor, 0f);
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionViewMatrixTrans, MATRIX_PROJECTION_VIEW);
		UniformUtils.setUniformMatrix(ViewMatrixTrans, MATRIX_VIEW);

		MATRIX_VIEW_INVERT_TRANSPOSE.load(MATRIX_VIEW);
		MATRIX_VIEW_INVERT_TRANSPOSE.invert();
		MATRIX_VIEW_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(viewMatrixTrans_InverseTranspose, MATRIX_VIEW_INVERT_TRANSPOSE);
	}

	@Override
	protected String getVertexName()
	{
		return "MeshBatchVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "WorldFragment.frag";
	}

	@Override
	public String getName()
	{
		return "MeshBatchShader";
	}
	
}
