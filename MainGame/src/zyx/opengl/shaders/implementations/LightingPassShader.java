package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.camera.Camera;
import zyx.opengl.lighs.ILight;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;

public class LightingPassShader extends AbstractShader
{
	private static final Matrix4f[] MATRIX_PROJECTION_VIEWS = new Matrix4f[]
	{
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_1,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_2,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_3,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_4
	};
	
	private int lightDirectionUniform;
	private int lightPositionsUniform;
	private int lightColorsUniform;
	private int lightPowersUniform;
	private int sunProjViewsUniform;
	private int uvQuadrantOffsetUniform;
	private int uvQuadrantMinUniform;
	private int uvQuadrantMaxUniform;
	
	private int lastLightCount = -1;
	private Vector3f[] lightPositions;
	private Vector3f[] lightColors;
	private int[] lightPowers;
	private int camPosUniform;

	public LightingPassShader(Object lock)
	{
		super(lock);
	}

	@Override
	public void upload()
	{
		Vector3f pos = Camera.getInstance().getPosition(false, null);
		UniformUtils.setUniform3F(camPosUniform, pos.x, pos.y, pos.z);
	}

	@Override
	protected void postLoading()
	{
		lightDirectionUniform = UniformUtils.createUniform(program, "lightDir");
		lightPositionsUniform = UniformUtils.createUniform(program, "lightPositions");
		lightColorsUniform = UniformUtils.createUniform(program, "lightColors");
		lightPowersUniform = UniformUtils.createUniform(program, "lightPowers");
		sunProjViewsUniform = UniformUtils.createUniform(program, "sunProjViews");
		uvQuadrantOffsetUniform = UniformUtils.createUniform(program, "shadowUvOffsetPerQuadrant");
		uvQuadrantMinUniform = UniformUtils.createUniform(program, "uvLimitsMinPerQuadrant");
		uvQuadrantMaxUniform = UniformUtils.createUniform(program, "uvLimitsMaxPerQuadrant");
		camPosUniform = UniformUtils.createUniform(program, "camPos");
		
		//0  1
		//2  3
		Vector2f[] uvOffsets = new Vector2f[]
		{
			new Vector2f(0.0f, 0.5f),
			new Vector2f(0.5f, 0.5f),
			new Vector2f(0.0f, 0.0f),
			new Vector2f(0.5f, 0.0f),
		};
		UniformUtils.setUniformArrayF(uvQuadrantOffsetUniform, uvOffsets);
		
		Vector2f[] uvLimitsMin = new Vector2f[]
		{
			new Vector2f(0.0f, 0.5f),
			new Vector2f(0.5f, 0.5f),
			new Vector2f(0.0f, 0.0f),
			new Vector2f(0.5f, 0.0f),
		};
		UniformUtils.setUniformArrayF(uvQuadrantMinUniform, uvLimitsMin);
		
		Vector2f[] uvLimitsMax = new Vector2f[]
		{
			new Vector2f(0.5f, 1.0f),
			new Vector2f(1.0f, 1.0f),
			new Vector2f(0.5f, 0.5f),
			new Vector2f(1.0f, 0.5f),
		};
		UniformUtils.setUniformArrayF(uvQuadrantMaxUniform, uvLimitsMax);
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
				lightPowers[i] = 0;
			}
			else
			{
				light.getLightPosition(lightPositions[i]);
				light.getColorVector(lightColors[i]);
				lightPowers[i] = light.getPower();
			}

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

	public void uploadSunMatrix()
	{
		bind();
		UniformUtils.setUniformMatrix(sunProjViewsUniform, MATRIX_PROJECTION_VIEWS);
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
