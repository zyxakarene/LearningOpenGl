package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.shaders.AbstractShader;

public class WorldShader extends AbstractShader
{

	public static final Matrix4f MATRIX_PROJECTION = new Matrix4f();
	public static final Matrix4f MATRIX_VIEW = new Matrix4f();
	public static final Matrix4f MATRIX_MODEL = new Matrix4f();
	public static final Matrix4f MATRIX_MODEL_INVERT_TRANSPOSE = new Matrix4f();
	
	public final Matrix4f[] BONES = new Matrix4f[20];
	public final Matrix4f[] INVERT_BONES = new Matrix4f[20];

	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int modelMatrixTrans;
	private int boneMatrixTrans;
	private int lightDirection;
	
	private int modelMatrixTrans_InverseTranspose;
	private int boneMatrixTrans_InverseTranspose;

	public WorldShader(Object lock)
	{
		super(lock);
		
		for (int i = 0; i < BONES.length; i++)
		{
			BONES[i] = new Matrix4f();
			INVERT_BONES[i] = new Matrix4f();
		}
		
		Joint.setBones(BONES, INVERT_BONES);
	}

	@Override
	protected void postLoading()
	{
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		boneMatrixTrans = UniformUtils.createUniform(program, "bones");
		
		boneMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "bonesInverseTranspose");
		modelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "modelInverseTranspose");
		
		lightDirection = UniformUtils.createUniform(program, "lightDir");
	}

	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		
		MATRIX_MODEL_INVERT_TRANSPOSE.load(MATRIX_MODEL);
		MATRIX_MODEL_INVERT_TRANSPOSE.invert();
		MATRIX_MODEL_INVERT_TRANSPOSE.transpose();
		UniformUtils.setUniformMatrix(modelMatrixTrans_InverseTranspose, MATRIX_MODEL_INVERT_TRANSPOSE);
		
		synchronized(BONES)
		{
			UniformUtils.setUniformMatrix(boneMatrixTrans, BONES);
			UniformUtils.setUniformMatrix(boneMatrixTrans_InverseTranspose, INVERT_BONES);
		}
	}
	
	public void uploadLightDirection(Vector3f direction)
	{
		bind();
		UniformUtils.setUniform3F(lightDirection, direction.x, direction.y, direction.z);
	}

	public void uploadBones()
	{
		synchronized(BONES)
		{
			UniformUtils.setUniformMatrix(boneMatrixTrans, BONES);
			UniformUtils.setUniformMatrix(boneMatrixTrans_InverseTranspose, INVERT_BONES);
		}
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

}
