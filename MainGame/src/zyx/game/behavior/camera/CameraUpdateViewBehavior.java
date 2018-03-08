package zyx.game.behavior.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class CameraUpdateViewBehavior extends Behavior
{

	private final Matrix4f viewMatrix;
	private final Vector3f tempMovement;

	private Vector3f cameraPosition;
	private Vector3f cameraRotation;
	private Vector4f cameraRotationRad;

	public CameraUpdateViewBehavior()
	{
		super(BehaviorType.CAMERA_UPDATE_VIEW);
		
		viewMatrix = WorldShader.MATRIX_VIEW;
		tempMovement = new Vector3f();
	}

	@Override
	public void initialize()
	{
		cameraPosition = new Vector3f();
		cameraRotation = new Vector3f();
		cameraRotationRad = new Vector4f();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		gameObject.getPosition(false, cameraPosition);
		gameObject.getRotation(false, cameraRotation);
		
		cameraRotationRad.x = FloatMath.toRadians(cameraRotation.x);
		cameraRotationRad.y = FloatMath.toRadians(cameraRotation.y);
		cameraRotationRad.z = FloatMath.toRadians(cameraRotation.z);
		cameraRotationRad.w = FloatMath.toRadians(cameraRotation.x + 90);
		
		viewMatrix.setIdentity();
		viewMatrix.rotate(cameraRotationRad.x, GeometryUtils.ROTATION_X);
		viewMatrix.rotate(cameraRotationRad.y, GeometryUtils.ROTATION_Y);
		viewMatrix.rotate(cameraRotationRad.z, GeometryUtils.ROTATION_Z);

		viewMatrix.translate(cameraPosition);

		float dX = FloatMath.sin(cameraRotationRad.z) * FloatMath.cos(cameraRotationRad.w) * 0.1f;
		float dY = FloatMath.cos(cameraRotationRad.z) * FloatMath.cos(cameraRotationRad.w) * 0.1f;
		float dZ = FloatMath.cos(cameraRotationRad.x) * 0.1f;
		tempMovement.set(-dX * 10, -dY * 10, dZ * 10);
		viewMatrix.translate(tempMovement);

	}

}
