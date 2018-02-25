package zyx.game.components.world.camera;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.camera.CameraUpdateLightbehavior;
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

//		addBehavior(new CameraFreeFlyBehavior());
		addBehavior(new CameraUpdateViewBehavior());
		addBehavior(new CameraUpdateLightbehavior());
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

	@Override
	public Vector3f getRotation(Vector3f out)
	{
		return out.set(camera.getRotation());
	}

	@Override
	public Vector3f getPosition(Vector3f out)
	{
		return out.set(camera.getPosition());
	}

	@Override
	public void setPosition(Vector3f rotation)
	{
		camera.getPosition().set(rotation);
	}

	@Override
	public void setRotation(Vector3f position)
	{
		camera.getRotation().set(position);
	}
	
	
	

	
}
