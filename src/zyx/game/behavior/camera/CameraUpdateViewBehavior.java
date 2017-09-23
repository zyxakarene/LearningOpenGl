package zyx.game.behavior.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
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

	public CameraUpdateViewBehavior()
	{
		super(BehaviorType.CAMERA_UPDATE_VIEW);
		
		viewMatrix = WorldShader.MATRIX_VIEW;
		tempMovement = new Vector3f();
	}

	@Override
	public void initialize()
	{
		cameraPosition = gameObject.getPosition();
		cameraRotation = gameObject.getRotation();
	}

	@Override
	public void update(int elapsedTime)
	{
		viewMatrix.setIdentity();
		viewMatrix.rotate(FloatMath.toRadians(cameraRotation.x), GeometryUtils.ROTATION_X);
		viewMatrix.rotate(FloatMath.toRadians(cameraRotation.y), GeometryUtils.ROTATION_Y);
		viewMatrix.rotate(FloatMath.toRadians(cameraRotation.z), GeometryUtils.ROTATION_Z);

		viewMatrix.translate(cameraPosition);

		float dX = FloatMath.sin(FloatMath.toRadians(cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(cameraRotation.x + 90)) * 0.1f;
		float dY = FloatMath.cos(FloatMath.toRadians(cameraRotation.z)) * FloatMath.cos(FloatMath.toRadians(cameraRotation.x + 90)) * 0.1f;
		float dZ = FloatMath.cos(FloatMath.toRadians(cameraRotation.x)) * 0.1f;
		tempMovement.set(-dX * 10, -dY * 10, dZ * 10);
		viewMatrix.translate(tempMovement);

	}

}
