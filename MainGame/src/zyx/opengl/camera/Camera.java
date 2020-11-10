package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.GameConstants;
import zyx.utils.math.Vector2Int;

public class Camera
{
	private static final Vector3f HELPER_LOOK_AT_TARGET = new Vector3f();
	private static final Vector3f HELPER_LOOK_AT_MY_POS = new Vector3f();
	private static final Camera INSTANCE = new Camera();

	public static Camera getInstance()
	{
		return INSTANCE;
	}

	private boolean initialized;
	private ViewFrustum frustum;
	
	private WorldObject target;
	
	private Camera()
	{
		initialized = false;
		frustum = new ViewFrustum();
	
		target = World3D.getInstance();
	}

	private void onScreenSizeChanged(Vector2Int data)
	{
		createMatrices();
	}
	
	public void setViewObject(WorldObject target)
	{
		this.target = target;
	}
	
	public void clearViewObject()
	{
		this.target = World3D.getInstance();
	}
	
	public void initialize()
	{
		if (initialized)
		{
			return;
		}
		
		initialized = true;
		createMatrices();
		
		ScreenSize.addListener(this::onScreenSizeChanged);
	}

	protected void createMatrices()
	{
		float w = ScreenSize.windowWidth;
		float h = ScreenSize.windowHeight;
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

	public Vector3f getRotation(boolean local, Vector3f out)
	{
		return target.getRotation(local, out);
	}

	public Vector3f getPosition(boolean local, Vector3f out)
	{
		return target.getPosition(local, out);
	}

	public void setPosition(boolean local, Vector3f out)
	{
		target.setPosition(local, out);
	}

	public void setRotation(Vector3f rotation)
	{
		target.setRotation(rotation);
	}

	public void setDir(boolean local, Vector3f dir)
	{
		target.setDir(local, dir);
	}

	public Vector3f getDir(boolean local, Vector3f out)
	{
		return target.getDir(local, out);
	}
	
	public void lookAt(float x, float y, float z)
	{
		HELPER_LOOK_AT_TARGET.set(x, y, z);
		getPosition(false, HELPER_LOOK_AT_MY_POS);

		Vector3f.sub(HELPER_LOOK_AT_TARGET, HELPER_LOOK_AT_MY_POS, HELPER_LOOK_AT_TARGET);
		if (HELPER_LOOK_AT_TARGET.x != 0 || HELPER_LOOK_AT_TARGET.y != 0 || HELPER_LOOK_AT_TARGET.z != 0)
		{
			HELPER_LOOK_AT_TARGET.normalise();

			HELPER_LOOK_AT_MY_POS.x -= (HELPER_LOOK_AT_TARGET.x * 100f);
			HELPER_LOOK_AT_MY_POS.y -= (HELPER_LOOK_AT_TARGET.y * 100f);
			HELPER_LOOK_AT_MY_POS.z -= (HELPER_LOOK_AT_TARGET.z * 100f);
		}
		
		target.lookAt(HELPER_LOOK_AT_MY_POS);
	}

	public void lookAt(Vector3f pos)
	{
		lookAt(pos.x, pos.y, pos.z);
	}
	
	public void rotate(float x, float y, float z)
	{
		target.rotate(x, y, z);
	}
}
