package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.blocks.ShaderBlock;
import zyx.opengl.shaders.blocks.ShaderMatricesBlock;
import zyx.opengl.shaders.source.ShaderReplacement;

public class WorldShader extends AbstractShader implements IBoneShader
{

	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL_INVERT_TRANSPOSE = new Matrix4f();
	private static final Matrix4f MATRIX_VIEW_MODEL_INVERT_TRANSPOSE = new Matrix4f();

	public static int cubemapIndex = 0;
	
	private int modelMatrixTrans;
	private int cubemapColor;

	private int modelMatrixTrans_InverseTranspose;
	private int viewModelMatrixTrans_InverseTranspose;
	
	private BoneShaderObject boneShaderObject;

	public WorldShader(Object lock, int boneCount)
	{
		super(lock);
		boneShaderObject = new BoneShaderObject(boneCount);
	}
	
	public int GetBoneCount()
	{
		return boneShaderObject.boneCount;
	}

	@Override
	protected void postLoading()
	{
		cubemapColor = UniformUtils.createUniform(program, "cubemapColor");
		
		modelMatrixTrans = UniformUtils.createUniform(program, "model");

		modelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "modelInverseTranspose");
		viewModelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "viewModelInverseTranspose");
		
		boneShaderObject.postLoading(program);
		
		ShaderMatricesBlock matriceBlock = ShaderManager.getInstance().get(ShaderBlock.MATRICES);
		matriceBlock.link(program);
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);

		MATRIX_MODEL_INVERT_TRANSPOSE.load(MATRIX_MODEL);
		MATRIX_MODEL_INVERT_TRANSPOSE.invert();
		MATRIX_MODEL_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(modelMatrixTrans_InverseTranspose, MATRIX_MODEL_INVERT_TRANSPOSE);

		Matrix4f.mul(MATRIX_VIEW, MATRIX_MODEL, MATRIX_VIEW_MODEL_INVERT_TRANSPOSE);
		MATRIX_VIEW_MODEL_INVERT_TRANSPOSE.invert();
		MATRIX_VIEW_MODEL_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(viewModelMatrixTrans_InverseTranspose, MATRIX_VIEW_MODEL_INVERT_TRANSPOSE);

		float cubemapColorFloat = cubemapIndex / 255f;
		UniformUtils.setUniformFloat(cubemapColor, cubemapColorFloat);
		
		boneShaderObject.uploadBones();
	}

	@Override
	protected String getVertexName()
	{
		return "WorldVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "WorldFragment.frag";
	}

	@Override
	public String getName()
	{
		return "WorldShader_" + boneShaderObject.boneCount;
	}

	@Override
	protected ShaderReplacement[] getVertexReplacements()
	{
		return boneShaderObject.getVertexReplacements();
	}
}
