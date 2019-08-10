package zyx.game.scene.game;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.saving.CubemapProcess;
import zyx.engine.components.meshbatch.MeshBatchEntity;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.engine.components.network.GameNetworkController;
import zyx.engine.components.tooltips.TestTooltip;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.components.world.GameLight;
import zyx.engine.scene.Scene;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.freefly.OnlinePositionSender;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.world.meshbatch.CubeEntity;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.process.ProcessQueue;
import zyx.game.vo.Gender;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.shapes.Sphere;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;
import zyx.utils.math.QuaternionUtils;

public class DragonScene extends Scene implements ICallback<ProcessQueue>
{

	public static DragonScene current;

	private ArrayList<GameObject> gameObjects;
	private boolean cubemapping;
	private ProcessQueue processQueue;

	private MeshObject testDragon;

	public DragonScene()
	{
		gameObjects = new ArrayList<>();
		current = this;
	}

	@Override
	protected void onPreloadResources()
	{
	}

	@Override
	protected void onInitialize()
	{
		NetworkChannel.sendRequest(NetworkCommands.LOGIN, "Zyx" + Math.random(), Gender.MALE);

		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");

		MeshObject dragon = new MeshObject();
		dragon.setScale(0.33f, 0.33f, 0.33f);
		dragon.load("mesh.dragon");
		world.addChild(dragon);
		testDragon = dragon;

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
		//spinner.addBehavior(new RotateBehavior());

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

		TooltipManager.getInstance().register(new TestTooltip(sphere1));
		TooltipManager.getInstance().register(new TestTooltip(sphere2));
		TooltipManager.getInstance().register(new TestTooltip(sphere3));
		TooltipManager.getInstance().register(new TestTooltip(sphere4));

		for (int i = 0; i < 100; i++)
		{
			MeshBatchEntity entityA = new CubeEntity();
			entityA.position.x = (FloatMath.random() * 200) - 100;
			entityA.position.y = (FloatMath.random() * 200) - 100;
			entityA.position.z = (FloatMath.random() * 200) - 100;
			entityA.scale = 3;

			float x = FloatMath.random() * 6.28319f;
			float y = FloatMath.random() * 6.28319f;
			float z = FloatMath.random() * 6.28319f;
			entityA.rotation = QuaternionUtils.toQuat(new Vector3f(x, y, z), null);

			MeshBatchManager.getInstance().addEntity(entityA);
		}
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

		if (KeyboardData.data.wasPressed(Keyboard.KEY_Q))
		{
			Vector3f dir = testDragon.getDir(false, null);
			Vector3f pos = testDragon.getPosition(false, null);
			Print.out("Dragon dir:", dir);
			Print.out("Dragon pos:", pos);
			float x = pos.x + (dir.x * 100);
			float y = pos.y + (dir.y * 100);
			float z = pos.z + (dir.z * 100);

			testDragon.lookAt(x, y, z);
			Vector3f postDir = testDragon.getDir(false, null);
			Print.out("Dragon PostDir:", postDir);
		}

		//Vector3f camPos = Camera.getInstance().getPosition(false, null);
		//testDragon.lookAt(camPos.x + 0.01f, camPos.y, camPos.z);
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
	protected BaseNetworkController createNetworkDispatcher()
	{
		return new GameNetworkController(playerHandler);
	}

	@Override
	protected void onDispose()
	{
		for (int i = 0; i < gameObjects.size(); i++)
		{
			GameObject obj = gameObjects.get(i);
			obj.dispose();
		}

		world.removeSkybox();
		CubemapManager.getInstance().clean();
		TooltipManager.getInstance().clean();
		MeshBatchManager.getInstance().clean();
		
		gameObjects.clear();
		gameObjects = null;

		camera.removeBehavior(BehaviorType.ONLINE_POSITION);
	}

	@Override
	public void onCallback(ProcessQueue data)
	{
		cubemapping = false;
		processQueue.dispose();
		processQueue = null;
	}

	public void onAuthed()
	{
		camera.addBehavior(new OnlinePositionSender());
	}
}
