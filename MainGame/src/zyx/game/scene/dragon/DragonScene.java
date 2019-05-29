package zyx.game.scene.dragon;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapProcess;
import zyx.engine.components.world.GameLight;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.CubemapResource;
import zyx.engine.scene.Scene;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.GameObject;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.process.ProcessQueue;
import zyx.opengl.GLUtils;
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
		preloadResource("cubemap.dragon");
	}

	@Override
	protected void onInitialize()
	{
		CubemapResource cubemap = ResourceManager.getInstance().<CubemapResource>getResourceAs("cubemap.dragon");
		cubemap.getContent().bind();
		
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
			
			lightContainer.addBehavior(new JiggleBehavior());
			
			gameObjects.add(lightContainer);
		}
		
		world.setSunRotation(new Vector3f(-33, -5, -21));
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.update(timestamp, elapsedTime);
		}
		
		if (KeyboardData.data.wasPressed(Keyboard.KEY_E))
		{
			Vector3f cameraRot = camera.getRotation(false, null);
			Print.out(cameraRot);
		}
		
		if (!cubemapping && KeyboardData.data.wasPressed(Keyboard.KEY_SPACE))
		{
			cubemapping = true;
			
			Vector3f cameraPos = camera.getPosition(false, null);
			
			processQueue = new ProcessQueue();
			processQueue.addProcess(new CubemapProcess(cameraPos));
			
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
