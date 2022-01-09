package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.blocks.ShaderBlock;
import zyx.opengl.shaders.blocks.ShaderMatricesBlock;

public class MeshBatchShader extends AbstractShader
{

	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW_INVERT_TRANSPOSE = new Matrix4f();

	private int viewMatrixTrans_InverseTranspose;

	public MeshBatchShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		viewMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "viewInverseTranspose");
		
		ShaderMatricesBlock matriceBlock = ShaderManager.getInstance().get(ShaderBlock.MATRICES);
		matriceBlock.link(program);
	}

	@Override
	public void upload()
	{
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
