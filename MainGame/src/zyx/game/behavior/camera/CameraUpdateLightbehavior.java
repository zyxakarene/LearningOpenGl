package zyx.game.behavior.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.cheats.Print;

public class CameraUpdateLightbehavior extends Behavior
{

	private WorldShader worldShader;

	public CameraUpdateLightbehavior()
	{
		super(BehaviorType.CAMERA_LIGHT);
	}

	@Override
	public void initialize()
	{
		worldShader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			CameraController controller = (CameraController) gameObject;

			float elevation = FloatMath.toRadians(controller.getRotation().z);
			float heading = FloatMath.toRadians(controller.getRotation().x);
			HELPER_DIR.x = FloatMath.cos(elevation) * FloatMath.sin(heading);

			elevation = FloatMath.toRadians(controller.getRotation().y);
			heading = FloatMath.toRadians(controller.getRotation().x);
			HELPER_DIR.z = FloatMath.cos(elevation) * FloatMath.cos(heading);

			elevation = FloatMath.toRadians(controller.getRotation().y);
			heading = FloatMath.toRadians(controller.getRotation().x);
			HELPER_DIR.y = FloatMath.cos(elevation) * FloatMath.sin(elevation);
			
			
			Matrix4f mat = new Matrix4f();
			mat.rotate(FloatMath.toRadians(gameObject.getRotation().x), GeometryUtils.ROTATION_X);
			mat.rotate(FloatMath.toRadians(gameObject.getRotation().y), GeometryUtils.ROTATION_Y);
			mat.rotate(FloatMath.toRadians(gameObject.getRotation().z), GeometryUtils.ROTATION_Z);
			Print.out(mat);
			
			Vector3f a = new Vector3f(mat.m20, mat.m21, mat.m22); // Left-handed column oriented: get z row.
			Vector3f b = new Vector3f(mat.m02,mat.m12,mat.m22); // Left-handed row oriented: get z column.
			Vector3f c = new Vector3f(-mat.m20,-mat.m21,-mat.m22); // OpenGL style matrix (right-handed, column oriented), get the -z row.
			Vector3f d = new Vector3f(mat.m02,mat.m12, mat.m22); // Right-handed row oriented: get -z column.

			Print.out(a);
			Print.out(b);
			Print.out(c);
			Print.out(d);
			worldShader.uploadLightDirection(d);
		}
	}

}
