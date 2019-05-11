package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class Camera extends WorldObject
{

	private static Camera instance = new Camera();

	public static Camera getInstance()
	{
		return instance;
	}

	private boolean initialized;
	private ViewFrustum frustum;
	
	private Camera()
	{
		super(Shader.WORLD);
		initialized = false;
		frustum = new ViewFrustum();
	}

	public void initialize()
	{
		if (initialized)
		{
			return;
		}
		
		initialized = true;
		Projection.createPerspective(70f, 0.001f, 2f, SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		Projection.createOrthographic(1f, 2f, SharedShaderObjects.UI_ORTHOGRAPHIC_PROJECTION, 2);
		
		Projection.createOrthographic(0.1f, 200f, SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION, 4f);
		
		RayPicker.getInstance().setProjectionMatrix(SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		
		setRotation(-90, 0, 0);

		Matrix4f mat = SharedShaderObjects.SUN_VIEW_TRANSFORM;
		Vector3f cameraPosition = new Vector3f(21.33304f, -67.95955f, 76.854935f);
		Vector3f cameraRotation = new Vector3f(-40.416027f, -4.268868E-6f, -11.350725f);
		
		cameraRotation.x = FloatMath.toRadians(cameraRotation.x);
		cameraRotation.y = FloatMath.toRadians(cameraRotation.y);
		cameraRotation.z = FloatMath.toRadians(cameraRotation.z);
		
		mat.setIdentity();
		mat.rotate(cameraRotation.x, GeometryUtils.ROTATION_X);
		mat.rotate(cameraRotation.y, GeometryUtils.ROTATION_Y);
		mat.rotate(cameraRotation.z, GeometryUtils.ROTATION_Z);

		cameraPosition.negate();
		mat.translate(cameraPosition);

	}
	
	public void getProjectionMatrix(Matrix4f out)
	{
		out.load(SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
	}
	
	public void getViewMatrix(Matrix4f out)
	{
		out.load(SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM);
	}

	public void setViewFrustum(Matrix4f matrix)
	{
		frustum.extractPlanesFrom(matrix);
	}

	public boolean isInView(Vector3f worldPosition, float radius)
	{
		return true;
		//return frustum.isInsideView(worldPosition, radius);
	}
}
