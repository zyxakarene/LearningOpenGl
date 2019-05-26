package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.SharedShaderObjects;

public class WorldShader extends BaseBoneShader
{

	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_PROJECTION_VIEW = SharedShaderObjects.WORLD_PROJECTION_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL_INVERT_TRANSPOSE = new Matrix4f();
	private static final Matrix4f MATRIX_VIEW_MODEL_INVERT_TRANSPOSE = new Matrix4f();

	private int ViewMatrixTrans;
	private int projectionViewMatrixTrans;
	private int modelMatrixTrans;
	private int debugColor;

	private int modelMatrixTrans_InverseTranspose;
	private int viewModelMatrixTrans_InverseTranspose;

	public WorldShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void onPostLoading()
	{
		debugColor = UniformUtils.createUniform(program, "debugColor");
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		projectionViewMatrixTrans = UniformUtils.createUniform(program, "projectionView");
		ViewMatrixTrans = UniformUtils.createUniform(program, "view");

		modelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "modelInverseTranspose");
		viewModelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "viewModelInverseTranspose");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformInt(debugColor, DebugDrawCalls.shouldHighlightWorld() ? 1 : 0);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(projectionViewMatrixTrans, MATRIX_PROJECTION_VIEW);
		UniformUtils.setUniformMatrix(ViewMatrixTrans, MATRIX_VIEW);

		MATRIX_MODEL_INVERT_TRANSPOSE.load(MATRIX_MODEL);
		MATRIX_MODEL_INVERT_TRANSPOSE.invert();
		MATRIX_MODEL_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(modelMatrixTrans_InverseTranspose, MATRIX_MODEL_INVERT_TRANSPOSE);

		Matrix4f.mul(MATRIX_VIEW, MATRIX_MODEL, MATRIX_VIEW_MODEL_INVERT_TRANSPOSE);
		MATRIX_VIEW_MODEL_INVERT_TRANSPOSE.invert();
		MATRIX_VIEW_MODEL_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(viewModelMatrixTrans_InverseTranspose, MATRIX_VIEW_MODEL_INVERT_TRANSPOSE);

		//uploadBones();
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
		return "WorldShader";
	}
	
}
