package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.math.Vector2Int;

public class MeshBatchDepthShader extends AbstractShader
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
	
	private int projectionViewMatsUniform;
	private int shadowOffsetsUniform;
	private int shadowOffsetMinUniform;
	private int shadowOffsetMaxUniform;
	private int currentQuadrantUniform;
	
	private Vector2f DepthSize;

	public MeshBatchDepthShader(Object lock)
	{
		super(lock);
		
		DepthSize = DepthRenderer.getInstance().GetSize();
		ScreenSize.addListener(this::OnScreenChangedSize);
	}

	private void OnScreenChangedSize(Vector2Int size)
	{
		DepthSize = DepthRenderer.getInstance().GetSize();
	}
	
	@Override
	protected void postLoading()
	{
		projectionViewMatsUniform = UniformUtils.createUniform(program, "projectionViews");
		shadowOffsetsUniform = UniformUtils.createUniform(program, "shadowOffsets");
		shadowOffsetMinUniform = UniformUtils.createUniform(program, "shadowOffsetMin");
		shadowOffsetMaxUniform = UniformUtils.createUniform(program, "shadowOffsetMax");
		currentQuadrantUniform = UniformUtils.createUniform(program, "currentQuadrant");
	}

	@Override
	public void upload()
	{
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
		
		float w = DepthSize.x;
		float h = DepthSize.y;
		
		//0  1
		//2  3
		switch (quadrant)
		{
			case QUADRANT_0:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, -0.5f, 0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, 0f, h/2f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, w/2f, h);
				break;
			}
			case QUADRANT_1:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, 0.5f, 0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, w/2f, h/2f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, w, h);
				break;
			}
			case QUADRANT_2:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, -0.5f, -0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, 0f, 0f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, w/2f, h/2f);
				break;
			}
			case QUADRANT_3:
			{
				UniformUtils.setUniform2F(shadowOffsetsUniform, 0.5f, -0.5f);
				UniformUtils.setUniform2F(shadowOffsetMinUniform, w/2f, 0f);
				UniformUtils.setUniform2F(shadowOffsetMaxUniform, w, h/2f);
				break;
			}
		}
	}

	@Override
	protected String getVertexName()
	{
		return "MeshBatchDepthVertex.vert";
	}

	@Override
	protected String getFragmentName()
	{
		return "DepthFragment.frag";
	}

	@Override
	public String getName()
	{
		return "MeshBatchDepthShader";
	}
}
