package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.shaders.AbstractShader;

public class ParticleShader extends AbstractShader
{

	public static final Matrix4f MATRIX_PROJECTION = WorldShader.MATRIX_PROJECTION;
	public static final Matrix4f MATRIX_VIEW = WorldShader.MATRIX_VIEW;
	public static final Vector3f VECTOR_POS = new Vector3f();
	
	private static final Vector3f HELPER_RIGHT = new Vector3f();
	private static final Vector3f HELPER_UP = new Vector3f();

	private int projectionMatrixTrans;
	private int viewMatrixTrans;
	private int cameraRightTrans;
	private int cameraUpTrans;
	private int positionTrans;
	private int sizeTrans;

	public ParticleShader(Object lock)
	{
		super(lock);
	}

	@Override
	protected void postLoading()
	{
		projectionMatrixTrans = UniformUtils.createUniform(program, "projection");
		viewMatrixTrans = UniformUtils.createUniform(program, "view");
		
		cameraRightTrans = UniformUtils.createUniform(program, "CameraRight_worldspace");
		cameraUpTrans = UniformUtils.createUniform(program, "CameraUp_worldspace");
		positionTrans = UniformUtils.createUniform(program, "BillboardPos");
		sizeTrans = UniformUtils.createUniform(program, "BillboardSize");
	}

	public void upload()
	{
		UniformUtils.setUniformMatrix(projectionMatrixTrans, MATRIX_PROJECTION);
		UniformUtils.setUniformMatrix(viewMatrixTrans, MATRIX_VIEW);
		
//		Camera.getInstance().controller.getRight(false, HELPER_VEC1);
//		Camera.getInstance().controller.getUp(false, HELPER_VEC2);
		
HELPER_UP.x = 1;
HELPER_UP.y = 0;
HELPER_UP.z = 0;
		
HELPER_RIGHT.x = 0;
HELPER_RIGHT.y = -1;
HELPER_RIGHT.z = 0;

		UniformUtils.setUniform3F(cameraRightTrans, HELPER_RIGHT.x, HELPER_RIGHT.y, HELPER_RIGHT.z);
		UniformUtils.setUniform3F(cameraUpTrans, HELPER_UP.x, HELPER_UP.y, HELPER_UP.z);
		UniformUtils.setUniform3F(positionTrans, VECTOR_POS.x, VECTOR_POS.y, VECTOR_POS.z);
		UniformUtils.setUniform2F(sizeTrans, 1, 1);
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

}
