package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.GameConstants;
import zyx.utils.math.Vector2Int;

public class Camera extends WorldObject
{

	private static Camera instance = new Camera();

	public static Camera getInstance()
	{
		return instance;
	}

	private boolean initialized;
	private ViewFrustum frustum;
	
	private ICallback<Vector2Int> onScreenSizeChanged;
	
	private Camera()
	{
		initialized = false;
		frustum = new ViewFrustum();
		
		onScreenSizeChanged = (Vector2Int data) ->
		{
			createMatrices();
		};
	}

	public void initialize()
	{
		if (initialized)
		{
			return;
		}
		
		initialized = true;
		createMatrices();
		
		setRotation(-90, 0, 0);
		
		ScreenSize.addListener(onScreenSizeChanged);
	}

	protected void createMatrices()
	{
		float w = ScreenSize.width;
		float h = ScreenSize.height;
		Projection.createPerspective(w, h, GameConstants.FOV, 1f, 1000f, SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
		Projection.createOrthographic(1f, 2f, 2, SharedShaderObjects.UI_ORTHOGRAPHIC_PROJECTION);
		
		Projection.createOrthographic(0.1f, 200f, 4f, SharedShaderObjects.SUN_ORTHOGRAPHIC_PROJECTION_CASCADE_1);
		
		RayPicker.getInstance().setProjectionMatrix(SharedShaderObjects.WORLD_PERSPECTIVE_PROJECTION);
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
