package zyx.game.components.world.camera;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.camera.CameraUpdateLightbehavior;
import zyx.game.components.GameObject;
import zyx.game.controls.lights.LightsManager;
import zyx.opengl.camera.Camera;

public class CameraController extends GameObject
{

	private Camera camera;

	public CameraController()
	{
		camera = Camera.getInstance();

		addBehavior(new CameraUpdateLightbehavior());
		
		LightsManager.getInstane().setSource(this);
	}

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		return camera.getPosition(local, out);
	}

	@Override
	public Vector3f getRotation(boolean local, Vector3f out)
	{
		return camera.getRotation(local, out);
	}

	@Override
	public Vector3f getDir(boolean local, Vector3f out)
	{
		return camera.getDir(local, out);
	}
	
	@Override
	public void setRotation(Vector3f rot)
	{
		camera.setRotation(rot);
	}

	@Override
	public void setPosition(boolean local, Vector3f pos)
	{
		camera.setPosition(local, pos);
	}

	@Override
	public void setDir(boolean local, Vector3f dir)
	{
		camera.setDir(local, dir);
	}
	
	
}
