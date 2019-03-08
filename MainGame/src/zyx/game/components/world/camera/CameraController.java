package zyx.game.components.world.camera;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.camera.CameraUpdateLightbehavior;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.CameraFreeFlyBehavior;
import zyx.game.components.GameObject;
import zyx.game.controls.lights.LightsManager;
import zyx.opengl.camera.Camera;

public class CameraController extends GameObject
{

	private Camera camera;

	public CameraController()
	{
		camera = Camera.getInstance();

		addBehavior(new CameraFreeFlyBehavior());
		addBehavior(new CameraUpdateViewBehavior());
		addBehavior(new CameraUpdateLightbehavior());
		
		LightsManager.getInstane().setSource(this);
	}

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		return Camera.getInstance().getPosition(local, out);
	}

	@Override
	public Vector3f getRotation(boolean local, Vector3f out)
	{
		return Camera.getInstance().getRotation(local, out);
	}

	@Override
	public void setRotation(Vector3f rot)
	{
		Camera.getInstance().setRotation(rot);
	}

	@Override
	public void setPosition(boolean local, Vector3f pos)
	{
		Camera.getInstance().setPosition(local, pos);
	}
}
