package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class WorldShader extends AbstractShader
{

	private static final Matrix4f MATRIX_PROJECTION_VIEW = SharedShaderObjects.SHARED_PROJECTION_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL_INVERT_TRANSPOSE = new Matrix4f();
	
	public final Matrix4f[] BONES = new Matrix4f[20];
	public final Matrix4f[] INVERT_BONES = new Matrix4f[20];

	private int projectionViewMatrixTrans;
	private int modelMatrixTrans;
	private int boneMatrixTrans;
	private int lightDirection;
	private int debugColor;
	
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
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		boneMatrixTrans = UniformUtils.createUniform(program, "bones");
		projectionViewMatrixTrans = UniformUtils.createUniform(program, "projectionView");
		
		boneMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "bonesInverseTranspose");
		modelMatrixTrans_InverseTranspose = UniformUtils.createUniform(program, "modelInverseTranspose");
		
		lightDirection = UniformUtils.createUniform(program, "lightDir");
		debugColor = UniformUtils.createUniform(program, "debugColor");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformInt(debugColor, DebugDrawCalls.shouldHighlightWorld() ? 1 : 0);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(projectionViewMatrixTrans, MATRIX_PROJECTION_VIEW);
		
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
	
	@Override
	public String getName()
	{
		return "WorldShader";
	}

}
