package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.lighs.ILight;
import zyx.opengl.shaders.AbstractShader;

public class LightingPassShader extends AbstractShader
{

	private int positionTexUniform;
	private int normalTexUniform;
	private int albedoTexUniform;

	private int lightDirectionUniform;
	private int lightPositionsUniform;
	private int lightColorsUniform;
	private int lightPowersUniform;
	
	private int lastLightCount = -1;
	private Vector3f[] lightPositions;
	private Vector3f[] lightColors;
	private int[] lightPowers;

	public LightingPassShader(Object lock)
	{
		super(lock);
	}

	@Override
	public void upload()
	{
	}

	@Override
	protected void postLoading()
	{
		positionTexUniform = UniformUtils.createUniform(program, "gPosition");
		normalTexUniform = UniformUtils.createUniform(program, "gNormal");
		albedoTexUniform = UniformUtils.createUniform(program, "gAlbedoSpec");

		lightDirectionUniform = UniformUtils.createUniform(program, "lightDir");
		lightPositionsUniform = UniformUtils.createUniform(program, "lightPositions");
		lightColorsUniform = UniformUtils.createUniform(program, "lightColors");
		lightPowersUniform = UniformUtils.createUniform(program, "lightPowers");

		UniformUtils.setUniformInt(positionTexUniform, 0);
		UniformUtils.setUniformInt(normalTexUniform, 1);
		UniformUtils.setUniformInt(albedoTexUniform, 2);
	}

	public void uploadLights(ILight[] lights)
	{
		if (lastLightCount != lights.length)
		{
			lastLightCount = lights.length;
			
			lightColors = new Vector3f[lastLightCount];
			lightPositions = new Vector3f[lastLightCount];
			lightPowers = new int[lastLightCount];
			
			for (int i = 0; i < lastLightCount; i++)
			{
				lightColors[i] = new Vector3f();
				lightPositions[i] = new Vector3f();
			}
		}
		
		bind();
		for (int i = 0; i < lastLightCount; i++)
		{
			ILight light = lights[i];

			if (light == null)
			{
				continue;
			}

			light.getLightPosition(lightPositions[i]);
			light.getColorVector(lightColors[i]);
			lightPowers[i] = light.getPower();
		}

		UniformUtils.setUniformArrayF(lightPositionsUniform, lightPositions);
		UniformUtils.setUniformArrayF(lightColorsUniform, lightColors);
		UniformUtils.setUniformArrayI(lightPowersUniform, lightPowers);
	}

	public void uploadLightDirection(Vector3f direction)
	{
		bind();
		UniformUtils.setUniform3F(lightDirectionUniform, direction.x, direction.y, direction.z);
	}

	@Override
	public String getName()
	{
		return "LightingPassShader";
	}

	@Override
	protected String getVertexName()
	{
		return "LightingPassVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "LightingPassFragment.frag";
	}
}
