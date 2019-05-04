package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.lighs.ILight;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.AbstractShader;
import zyx.utils.Color;

public class LightingPassShader extends AbstractShader
{

	private int positionTexUniform;
	private int normalTexUniform;
	private int albedoTexUniform;

	private int lightDirection;
	private int lightPositions;
	private int lightColors;

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

		lightDirection = UniformUtils.createUniform(program, "lightDir");
		lightPositions = UniformUtils.createUniform(program, "lightPositions");
		lightColors = UniformUtils.createUniform(program, "lightColors");

		UniformUtils.setUniformInt(positionTexUniform, 0);
		UniformUtils.setUniformInt(normalTexUniform, 1);
		UniformUtils.setUniformInt(albedoTexUniform, 2);
	}

	public void uploadLights(ILight[] lights)
	{
		bind();
		Vector3f[] pos = new Vector3f[lights.length];
		Vector3f[] color = new Vector3f[lights.length];
		for (int i = 0; i < pos.length; i++)
		{
			ILight light = lights[i];

			if (light == null)
			{
				continue;
			}

			Vector3f lightPos = new Vector3f();
			light.getLightPosition(lightPos);
			pos[i] = lightPos;

			Vector3f lightCol = new Vector3f();
			int col = light.getColor();
			Color.toVector(col, lightCol);

			color[i] = lightCol;
		}

		UniformUtils.setUniformArrayF(lightPositions, pos);
		UniformUtils.setUniformArrayF(lightColors, color);
	}

	public void uploadLights(Vector3f pos)
	{
		bind();
		Vector3f[] positions = new Vector3f[2];
		positions[0] = pos;
		positions[1] = new Vector3f(0, 0, 0);
		UniformUtils.setUniformArrayF(lightPositions, positions);

		positions[0] = new Vector3f(1, 0, 0);
		positions[1] = new Vector3f(0, 1, 0);
		UniformUtils.setUniformArrayF(lightColors, positions);
	}

	public void uploadLightDirection(Vector3f direction)
	{
		bind();
		UniformUtils.setUniform3F(lightDirection, direction.x, direction.y, direction.z);
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
