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
	
	private int timeUniform;
	private int instancesUniform;
	private int gravityUniform;
	private int areaXUniform;
	private int areaYUniform;
	private int areaZUniform;
	private int speedUniform;
	private int speedVarianceUniform;
	private int startColorUniform;
	private int endColorUniform;
	private int startScaleUniform;
	private int endScaleUniform;
	private int scaleVarianceUniform;
	private int lifespanUniform;
	private int lifespanVarianceUniform;

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
		
		timeUniform = UniformUtils.createUniform(program, "time");
		instancesUniform = UniformUtils.createUniform(program, "instances");
		gravityUniform = UniformUtils.createUniform(program, "gravity");
		areaXUniform = UniformUtils.createUniform(program, "areaX");
		areaYUniform = UniformUtils.createUniform(program, "areaY");
		areaZUniform = UniformUtils.createUniform(program, "areaZ");
		speedUniform = UniformUtils.createUniform(program, "speed");
		speedVarianceUniform = UniformUtils.createUniform(program, "speedVariance");
		startColorUniform = UniformUtils.createUniform(program, "startColor");
		endColorUniform = UniformUtils.createUniform(program, "endColor");
		startScaleUniform = UniformUtils.createUniform(program, "startScale");
		endScaleUniform = UniformUtils.createUniform(program, "endScale");
		scaleVarianceUniform = UniformUtils.createUniform(program, "scaleVariance");
		lifespanUniform = UniformUtils.createUniform(program, "lifespan");
		lifespanVarianceUniform = UniformUtils.createUniform(program, "lifespanVariance");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(rotationTrans, MATRIX_ROTATION);
		
		UniformUtils.setUniformFloat(timeUniform, elapsedTime);
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
