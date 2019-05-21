package zyx.game.scene.matrix;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.impl.JsonResource;
import zyx.engine.scene.Scene;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.camera.Camera;

public class MatrixScene extends Scene implements IResourceReady<JsonResource>
{
	
	private MeshObject parent1;
	private MeshObject parent2;
	private MeshObject parent3;
	private MeshObject obj;
	private int rot;

	@Override
	protected void onPreloadResources()
	{
		preloadResource("mesh.simple.box");
		preloadResource("sound.Explosion");
		preloadResource("flat_bg");
	}
	
	@Override
	protected void onInitialize()
	{
		Vector3f dir1 = new Vector3f(45, 45, 0);
		dir1.normalise();
		Vector3f dir2 = new Vector3f(0, 50, 50);
		dir2.normalise();
		Vector3f dir3 = new Vector3f(33, 33, 33);
		dir3.normalise();
		
		parent1 = new MeshObject();
		parent1.load("mesh.debug");
		parent1.setPosition(true, 0, 0, 0);
		
		parent2 = new MeshObject();
		parent2.load("mesh.debug");
		parent2.setDir(true, dir2);
		parent2.setPosition(true, 0, 10, 0);
		
		parent3 = new MeshObject();
		parent3.load("mesh.debug");
		parent3.setDir(true, dir3);
		parent3.setPosition(true, 10, 0, 0);
		
		obj = new MeshObject();
		obj.load("mesh.debug");
		obj.setPosition(true, 0, 0, 10);
		obj.setScale(0.5f, 0.5f, 0.5f);
		
		parent1.addChild(parent2);
		parent2.addChild(parent3);
		parent3.addChild(obj);
		world.addChild(parent1);

		rot = 0;
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_RIGHT))
		{
			rot += 15;
			parent1.setRotZ(rot);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_LEFT))
		{
			rot -= 15;
			parent1.setRotZ(rot);
		}
		
		obj.update(timestamp, elapsedTime);
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			Vector3f cameraPos = camera.getPosition(false, null);
			parent1.lookAt(cameraPos.x, cameraPos.y, cameraPos.z);
			parent2.lookAt(cameraPos.x, cameraPos.y, cameraPos.z);
			parent3.lookAt(cameraPos.x, cameraPos.y, cameraPos.z);
			obj.lookAt(cameraPos.x, cameraPos.y, cameraPos.z);
		}
		
		if (KeyboardData.data.isDown(Keyboard.KEY_UP))
		{
			Vector3f dir = obj.getDir(true, null);
			Vector3f position = obj.getPosition(true, null);
			
			position.x += dir.x * 1;
			position.y += dir.y * 1;
			position.z += dir.z * 1;
			
			obj.setPosition(true, position);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_DOWN))
		{
			parent1.setPosition(false, 0, 0, 0);
			parent2.setPosition(false, 0, 0, 0);
			parent3.setPosition(false, 0, 0, 0);
			obj.setPosition(false, 0, 0, 0);
		}
	}

	@Override
	protected void onDispose()
	{
		if (parent1 != null)
		{
			parent1.dispose();
			parent1 = null;
		}
	}

	@Override
	public void onResourceReady(JsonResource resource)
	{
	}
}
