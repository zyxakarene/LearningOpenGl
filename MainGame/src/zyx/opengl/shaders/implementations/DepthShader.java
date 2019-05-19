package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.geometry.Plane;

public class DepthShader extends BaseBoneShader
{

	private static final Matrix4f MATRIX_PROJECTION_VIEW = SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM;
	private static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM;

	private int projectionViewMatrixTrans;
	private int modelMatrixTrans;
	private int sunNearPlaneUniform;

	public DepthShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void onPostLoading()
	{
		modelMatrixTrans = UniformUtils.createUniform(program, "model");
		projectionViewMatrixTrans = UniformUtils.createUniform(program, "projectionView");
		sunNearPlaneUniform = UniformUtils.createUniform(program, "sunNearPlane");
	}

	@Override
	public void upload()
	{
		UniformUtils.setUniformMatrix(modelMatrixTrans, MATRIX_MODEL);
		UniformUtils.setUniformMatrix(projectionViewMatrixTrans, MATRIX_PROJECTION_VIEW);

		//uploadBones();
	}

	public void uploadSunNearPlane(Plane near)
	{
		bind();
		UniformUtils.setUniform4F(sunNearPlaneUniform, near.a, near.b, near.c, near.d);
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
