package zyx.game.behavior.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.GLUtils;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import static zyx.opengl.shaders.SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION;
import static zyx.opengl.shaders.SharedShaderObjects.SUN_PROJECTION_VIEW_TRANSFORM;
import static zyx.opengl.shaders.SharedShaderObjects.SUN_VIEW_TRANSFORM;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class CameraUpdateLightbehavior extends Behavior
{

	private LightingPassShader lightShader;
	private WorldShader worldShader;
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
		worldShader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
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
			mat.translate(gameObject.getPosition(false, null));
			mat.rotate(FloatMath.toRadians(cameraRot.x), GeometryUtils.ROTATION_X);
			mat.rotate(FloatMath.toRadians(cameraRot.y), GeometryUtils.ROTATION_Y);
			mat.rotate(FloatMath.toRadians(cameraRot.z), GeometryUtils.ROTATION_Z);

			Vector3f a = new Vector3f(mat.m20, mat.m21, mat.m22); // Left-handed column oriented: get z row.
			Vector3f b = new Vector3f(mat.m02, mat.m12, mat.m22); // Left-handed row oriented: get z column.
			Vector3f c = new Vector3f(-mat.m20, -mat.m21, -mat.m22); // OpenGL style matrix (right-handed, column oriented), get the -z row.
			Vector3f d = new Vector3f(mat.m02, mat.m12, mat.m22); // Right-handed row oriented: get -z column.

			setSunShadowDir();

			GLUtils.errorCheck();
			lightShader.uploadLightDirection(d);
			GLUtils.errorCheck();
		}

		setSunPos();
	}

	private Vector4f cameraRotationRad = new Vector4f();
	private Vector3f cameraDir = new Vector3f();

	protected void setSunShadowDir()
	{
		Vector3f cameraRotation = gameObject.getRotation(false, null);

		cameraRotationRad.x = FloatMath.toRadians(cameraRotation.x);
		cameraRotationRad.y = FloatMath.toRadians(cameraRotation.y);
		cameraRotationRad.z = FloatMath.toRadians(cameraRotation.z);
		cameraRotationRad.w = FloatMath.toRadians(cameraRotation.x + 90);

		gameObject.getDir(false, cameraDir);
	}

	private void setSunPos()
	{
		Vector3f cameraPosition = gameObject.getPosition(false, null);

		cameraPosition.x -= cameraDir.x * 100;
		cameraPosition.y -= cameraDir.y * 100;
		cameraPosition.z -= cameraDir.z * 100;

		Matrix4f viewMatrix = SharedShaderObjects.SUN_VIEW_TRANSFORM;
		viewMatrix.setIdentity();
		cameraPosition.negate();
		viewMatrix.translate(cameraPosition);
		viewMatrix.rotate(cameraRotationRad.x, GeometryUtils.ROTATION_X);
		viewMatrix.rotate(cameraRotationRad.y, GeometryUtils.ROTATION_Y);
		viewMatrix.rotate(cameraRotationRad.z, GeometryUtils.ROTATION_Z);

		lightShader.uploadSunMatrix();
	}

}
