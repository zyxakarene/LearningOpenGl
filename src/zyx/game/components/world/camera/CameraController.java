package zyx.game.components.world.camera;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.CameraFreeFlyBehavior;
import zyx.game.components.GameObject;
import zyx.opengl.camera.Camera;

public class CameraController extends GameObject
{

	private Camera camera;

	public CameraController()
	{
		camera = Camera.getInstance();

		addBehavior(new CameraFreeFlyBehavior());
		addBehavior(new CameraUpdateViewBehavior());
	}

	@Override
	public Vector3f getPosition()
	{
		return camera.getPosition();
	}

	@Override
	public Vector3f getRotation()
	{
		return camera.getRotation();
	}

	
}
