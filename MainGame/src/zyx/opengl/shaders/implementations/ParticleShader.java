package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class ParticleShader extends AbstractShader
{

	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.SHARED_PROJECTION_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
	private static final Matrix4f MATRIX_ROTATION = SharedShaderObjects.SHARED_ROTATION_MATRIX;

	public static float elapsedTime = 0;
	
	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int modelMatrixTrans;
	private int rotationTrans;
	private int timeTrans;

	public ParticleShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		rotationTrans = UniformUtils.createUniform(program, "rotationMatrix");
		
		timeTrans = UniformUtils.createUniform(program, "time");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(rotationTrans, MATRIX_ROTATION);
		
		UniformUtils.setUniformFloat(timeTrans, elapsedTime);
	}

	@Override
	protected String getVertexName()
	{
		return "ParticleVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "ParticleFragment.frag";
	}

	@Override
	public String getName()
	{
		return "ParticleShader";
	}

}
