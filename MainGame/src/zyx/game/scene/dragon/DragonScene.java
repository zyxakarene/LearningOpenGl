package zyx.game.scene.dragon;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.misc.RotateBehavior;
import zyx.game.components.MeshObject;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.GLUtils;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugPoint;

public class DragonScene extends Scene
{

	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private GameObject globalLight;

	public DragonScene()
	{
	}

	@Override
	protected void onPreloadResources()
	{
		preloadResource("flat_bg");
	}

	@Override
	protected void onInitialize()
	{
		MeshObject dragon = new MeshObject();
		dragon.setScale(0.33f, 0.33f, 0.33f);
//		dragon.setY(20);
//		dragon.setZ(-10);
		dragon.load("mesh.dragon");
		world.addChild(dragon);
		
		dragon.addBehavior(new RotateBehavior());
		
		gameObjects.add(dragon);
		
		for (int i = 0; i < GameConstants.LIGHT_COUNT; i++)
		{
			GameObject lightContainer = new GameObject();
			GameLight light = new GameLight((int) (0xFFFFFF * Math.random()), 1);

			lightContainer.addChild(light);
			world.addChild(lightContainer);

			float x = -20f + (40f * FloatMath.random());
			float y = -20f + (40f * FloatMath.random());
			float z = (25f * FloatMath.random());
			lightContainer.setPosition(true, x, y, z);
			GLUtils.errorCheck();
			
			lightContainer.addBehavior(new JiggleBehavior());
			
			gameObjects.add(lightContainer);
			
			globalLight = lightContainer;
		}
		
		globalLight.setPosition(false, 0, 0, 0);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.update(timestamp, elapsedTime);
		}
		
		if (KeyboardData.data.isDown(Keyboard.KEY_SPACE))
		{
			globalLight.setPosition(false, camera.getPosition(false, null));
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
}
