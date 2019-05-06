package zyx.game.behavior.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class CameraUpdateLightbehavior extends Behavior
{

	private LightingPassShader lightShader;
	private Vector3f cameraRot;
	private CameraController controller;

	public CameraUpdateLightbehavior()
	{
		super(BehaviorType.CAMERA_LIGHT);
	}

	@Override
	public void initialize()
	{
		cameraRot = new Vector3f();
		lightShader = (LightingPassShader) ShaderManager.INSTANCE.get(Shader.DEFERED_LIGHT_PASS);
		controller = (CameraController) gameObject;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			controller.getRotation(true, cameraRot);
			
			float elevation = FloatMath.toRadians(cameraRot.z);
			float heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.x = FloatMath.cos(elevation) * FloatMath.sin(heading);

			elevation = FloatMath.toRadians(cameraRot.y);
			heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.z = FloatMath.cos(elevation) * FloatMath.cos(heading);

			elevation = FloatMath.toRadians(cameraRot.y);
			heading = FloatMath.toRadians(cameraRot.x);
			HELPER_DIR.y = FloatMath.cos(elevation) * FloatMath.sin(elevation);
			
			Matrix4f mat = new Matrix4f();
			mat.rotate(FloatMath.toRadians(cameraRot.x), GeometryUtils.ROTATION_X);
			mat.rotate(FloatMath.toRadians(cameraRot.y), GeometryUtils.ROTATION_Y);
			mat.rotate(FloatMath.toRadians(cameraRot.z), GeometryUtils.ROTATION_Z);
			
			Vector3f a = new Vector3f(mat.m20, mat.m21, mat.m22); // Left-handed column oriented: get z row.
			Vector3f b = new Vector3f(mat.m02,mat.m12,mat.m22); // Left-handed row oriented: get z column.
			Vector3f c = new Vector3f(-mat.m20,-mat.m21,-mat.m22); // OpenGL style matrix (right-handed, column oriented), get the -z row.
			Vector3f d = new Vector3f(mat.m02,mat.m12, mat.m22); // Right-handed row oriented: get -z column.

			lightShader.uploadLightDirection(d);
		}
	}

}
