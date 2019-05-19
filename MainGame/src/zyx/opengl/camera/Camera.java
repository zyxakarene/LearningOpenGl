package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
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
		Projection.createPerspective(GameConstants.FOV, 1f, 1000f, SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		Projection.createOrthographic(1f, 2f, 2, SharedShaderObjects.UI_ORTHOGRAPHIC_PROJECTION);
		
		Projection.createOrthographic(0.1f, 200f, 8f, SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION);
		
		RayPicker.getInstance().setProjectionMatrix(SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		
		setRotation(-90, 0, 0);
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
		return frustum.isInsideView(worldPosition, radius);
	}
}
