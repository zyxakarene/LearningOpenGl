package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class ParticleShader extends AbstractShader
{

	private static final Matrix4f MATRIX_PROJECTION = SharedShaderObjects.SHARED_PROJECTION_TRANSFORM;
	private static final Matrix4f MATRIX_VIEW = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;

	public static float elapsedTime = 0;
	public static float parentScale = 1;
	
	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int modelMatrixTrans;
	
	private int parentScaleUniform;
	
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
	private int rotationUniform;
	private int rotationVarianceUniform;

	public void uploadFromVo(LoadableParticleVO vo)
	{
		UniformUtils.setUniformFloat(instancesUniform, vo.instanceCount);
		UniformUtils.setUniform3F(gravityUniform, vo.gravity.x, vo.gravity.y, vo.gravity.z);
		UniformUtils.setUniform2F(areaXUniform, vo.areaX.x, vo.areaX.y);
		UniformUtils.setUniform2F(areaYUniform, vo.areaY.x, vo.areaY.y);
		UniformUtils.setUniform2F(areaZUniform, vo.areaZ.x, vo.areaZ.y);
		UniformUtils.setUniform3F(speedUniform, vo.speed.x, vo.speed.y, vo.speed.z);
		UniformUtils.setUniform3F(speedVarianceUniform, vo.speedVariance.x, vo.speedVariance.y, vo.speedVariance.z);
		UniformUtils.setUniform4F(startColorUniform, vo.startColor.x, vo.startColor.y, vo.startColor.z, vo.startColor.w);
		UniformUtils.setUniform4F(endColorUniform, vo.endColor.x, vo.endColor.y, vo.endColor.z, vo.endColor.w);
		UniformUtils.setUniformFloat(startScaleUniform, vo.startScale);
		UniformUtils.setUniformFloat(endScaleUniform, vo.endScale);
		UniformUtils.setUniformFloat(scaleVarianceUniform, vo.scaleVariance);
		UniformUtils.setUniformFloat(lifespanUniform, vo.lifespan);
		UniformUtils.setUniformFloat(lifespanVarianceUniform, vo.lifespanVariance);
		UniformUtils.setUniformFloat(rotationUniform, vo.rotation);
		UniformUtils.setUniformFloat(rotationVarianceUniform, vo.rotationVariance);
	}
	
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
		
		timeUniform = UniformUtils.createUniform(program, "time");
		parentScaleUniform = UniformUtils.createUniform(program, "parentScale");
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
		rotationUniform = UniformUtils.createUniform(program, "rotation");
		rotationVarianceUniform = UniformUtils.createUniform(program, "rotationVariance");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		
		UniformUtils.setUniformFloat(timeUniform, elapsedTime);
		UniformUtils.setUniformFloat(parentScaleUniform, parentScale);
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
