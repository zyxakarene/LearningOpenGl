package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.blocks.ShaderBlock;
import zyx.opengl.shaders.blocks.ShaderMatricesBlock;

public class SkyboxShader extends AbstractShader
{

	private static final Matrix4f MATRIX_VIEW = new Matrix4f();

	private int viewMatrixTrans;

	public SkyboxShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		viewMatrixTrans = UniformUtils.createUniform(program, "viewPos");
		
		ShaderMatricesBlock matriceBlock = ShaderManager.getInstance().get(ShaderBlock.MATRICES);
		matriceBlock.link(program);
	}
	
	@Override
	public void upload()
	{
		MATRIX_VIEW.load(SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM);
		MATRIX_VIEW.m30 = 0;
		MATRIX_VIEW.m31 = 0;
		MATRIX_VIEW.m32 = 0;
		
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
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
