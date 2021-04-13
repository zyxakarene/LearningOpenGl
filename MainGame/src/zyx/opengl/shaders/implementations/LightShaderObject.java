package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.camera.Camera;
import zyx.opengl.lighs.LightsProvider;
import zyx.opengl.shaders.SharedShaderObjects;

class LightShaderObject
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
	private int camPosUniform;

	LightShaderObject()
	{
	}

	public void upload()
	{
		Vector3f pos = Camera.getInstance().getPosition(false, null);
		UniformUtils.setUniform3F(camPosUniform, pos.x, pos.y, pos.z);

		UniformUtils.setUniformArrayF(lightPositionsUniform, LightsProvider.lightPositions);
		UniformUtils.setUniformArrayF(lightColorsUniform, LightsProvider.lightColors);
		UniformUtils.setUniformArrayI(lightPowersUniform, LightsProvider.lightPowers);
	}

	public void postLoading(int program)
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

	public void uploadSunMatrix()
	{
		UniformUtils.setUniformMatrix(sunProjViewsUniform, MATRIX_PROJECTION_VIEWS);
	}

	public void uploadLightDirection(Vector3f direction)
	{
		UniformUtils.setUniform3F(lightDirectionUniform, direction.x, direction.y, direction.z);
	}
}
