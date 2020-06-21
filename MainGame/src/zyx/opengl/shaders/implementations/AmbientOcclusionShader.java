package zyx.opengl.shaders.implementations;

import java.util.Random;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class AmbientOcclusionShader extends AbstractShader
{

	private int positionTexUniform;
	private int normalTexUniform;
	private int samplePositionUniform;
	private int projectionUniform;

	public AmbientOcclusionShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		positionTexUniform = UniformUtils.createUniform(program, "gPosition");
		normalTexUniform = UniformUtils.createUniform(program, "gNormal");
		samplePositionUniform = UniformUtils.createUniform(program, "samples");
		projectionUniform = UniformUtils.createUniform(program, "projection");

		UniformUtils.setUniformInt(positionTexUniform, 0);
		UniformUtils.setUniformInt(normalTexUniform, 1);
		
		Random random = new Random();
		Vector3f[] randomSamples = new Vector3f[64];
		for (int i = 0; i < randomSamples.length; i++)
		{
			float valueX = random.nextFloat() * 2f - 1;
			float valueY = random.nextFloat() * 2f - 1;
			float valueZ = random.nextFloat();
			
			Vector3f sample = new Vector3f(valueX, valueY, valueZ);
			randomSamples[i] = sample;
		}
		
		UniformUtils.setUniformArrayF(samplePositionUniform, randomSamples);
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionUniform, SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
	}

	@Override
	protected String getVertexName()
	{
		return "AmbientOcclusionVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "AmbientOcclusionFragment.frag";
	}

	@Override
	public String getName()
	{
		return "AmbientOcclusionShader";
	}
}
