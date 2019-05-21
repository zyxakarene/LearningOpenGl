package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.SharedShaderObjects;

public class DepthShader extends BaseBoneShader
{

	public final int QUADRANT_0 = 0;
	public final int QUADRANT_1 = 1;
	public final int QUADRANT_2 = 2;
	public final int QUADRANT_3 = 3;

	private static final Matrix4f[] MATRIX_PROJECTION_VIEWS = new Matrix4f[]
	{
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_1,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_2,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_3,
		SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM_CASCADE_4
	};
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;

	private int projectionViewMatsUniform;
	private int modelMatrixTrans;
	private int shadowOffsetsUniform;
	private int shadowOffsetMinUniform;
	private int shadowOffsetMaxUniform;
	private int currentQuadrantUniform;

	public DepthShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void onPostLoading()
	{
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		projectionViewMatsUniform = UniformUtils.createUniform(program, "projectionViews");
		shadowOffsetsUniform = UniformUtils.createUniform(program, "shadowOffsets");
		shadowOffsetMinUniform = UniformUtils.createUniform(program, "shadowOffsetMin");
		shadowOffsetMaxUniform = UniformUtils.createUniform(program, "shadowOffsetMax");
		currentQuadrantUniform = UniformUtils.createUniform(program, "currentQuadrant");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
	}

	public void uploadSunMatrix()
	{
		bind();
		UniformUtils.setUniformMatrix(projectionViewMatsUniform, MATRIX_PROJECTION_VIEWS);
	}

	public void prepareShadowQuadrant(int quadrant)
	{
		bind();
		
		UniformUtils.setUniformInt(currentQuadrantUniform, quadrant);
		
		//0  1
		//2  3
		switch (quadrant)
		{
			case QUADRANT_0:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, -0.5f, 0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, -1f, 0f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, 0f, 1f);
				break;
			}
			case QUADRANT_1:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, 0.5f, 0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, 0f, 0f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, 1f, 1f);
				break;
			}
			case QUADRANT_2:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, -0.5f, -0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, -1f, -1f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, 0f, 0f);
				break;
			}
			case QUADRANT_3:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, 0.5f, -0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, 0f, -1f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, 1f, 0f);
				break;
			}
		}
	}

	@Override
	protected String getVertexName()
	{
		return "DepthVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "DepthFragment.frag";
	}

	@Override
	public String getName()
	{
		return "DepthShader";
	}
}
