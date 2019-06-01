package zyx.game.scene.dragon;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.saving.CubemapProcess;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.Scene;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.GameObject;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.misc.RotateBehavior;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.process.ProcessQueue;
import zyx.opengl.GLUtils;
import zyx.opengl.buffers.BufferRenderer;
import zyx.opengl.models.implementations.shapes.Sphere;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;

public class DragonScene extends Scene implements ICallback<ProcessQueue>
{
	private ArrayList<GameObject> gameObjects;
	private boolean cubemapping;
	private ProcessQueue processQueue;
	
	public DragonScene()
	{
		gameObjects = new ArrayList<>();
	}

	@Override
	protected void onPreloadResources()
	{
	}

	@Override
	protected void onInitialize()
	{
		CubemapManager.getInstance().load("cubemap.dragon");
		
		MeshObject dragon = new MeshObject();
		dragon.setScale(0.33f, 0.33f, 0.33f);
		dragon.load("mesh.dragon");
		world.addChild(dragon);

		MeshObject platform = new MeshObject();
		platform.load("mesh.platform");
		world.addChild(platform);

		gameObjects.add(dragon);
		gameObjects.add(platform);

//		dragon.addBehavior(new RotateBehavior());
		
		for (int i = 0; i < GameConstants.LIGHT_COUNT; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 100);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -20f + (40f * FloatMath.random());
			float z = (25f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();
			
//			lightContainer.addBehavior(new JiggleBehavior());
			
			gameObjects.add(lightContainer);
		}
		
		world.setSunRotation(new Vector3f(-33, -5, -21));
		
		GameObject spinner = new GameObject();
//		spinner.addBehavior(new RotateBehavior());
		
		Sphere sphere1 = new Sphere(5);
		Sphere sphere2 = new Sphere(5);
		Sphere sphere3 = new Sphere(5);
		Sphere sphere4 = new Sphere(5);
		
		sphere1.setPosition(false, -20, -20, 10);
		sphere2.setPosition(false, 20, -20, 10);
		sphere3.setPosition(false, 20, 20, 10);
		sphere4.setPosition(false, -20, 20, 10);
		
		spinner.addChild(sphere1);
		spinner.addChild(sphere2);
		spinner.addChild(sphere3);
		spinner.addChild(sphere4);
		world.addChild(spinner);
		
		gameObjects.add(spinner);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.update(timestamp, elapsedTime);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_R))
		{
			int width = (int) (64 + (Math.random() * 1920 * 0.75));
			int height = (int) (64 + (Math.random() * 1080 * 0.75));
			ScreenSize.changeScreenSize(width, height);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			Vector3f cameraPos = camera.getPosition(false, null);
			Vector3f cameraRot = camera.getRotation(false, null);
			Print.out(cameraPos, cameraRot);
		}
		
		if (!cubemapping && KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			cubemapping = true;
			
			Vector3f[] positions = new Vector3f[]
			{
				new Vector3f(-20, -20, 10),
				new Vector3f(20, -20, 10),
				new Vector3f(20, 20, 10),
				new Vector3f(-20, 20, 10),
			};
			
			processQueue = new ProcessQueue();
			processQueue.addProcess(new CubemapProcess("dragon", positions));
			
			processQueue.start(this);
		}
		
		if (processQueue != null)
		{
			processQueue.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.dispose();
		}
		
		gameObjects.clear();
		gameObjects = null;
	}

	@Override
	public void onCallback(ProcessQueue data)
	{
		cubemapping = false;
		processQueue.dispose();
		processQueue = null;
	}
}
