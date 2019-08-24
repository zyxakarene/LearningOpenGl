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
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.player.OnlinePositionSender;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.SimpleMesh;
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
	private AnimatedMesh fridge;
	private GameObject player;

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
		dragon.setScale(0.033f, 0.033f, 0.033f);
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

			lightContainer.addBehavior(new JiggleBehavior());
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

		SimpleMesh steak = new SimpleMesh();
		SimpleMesh steakRaw = new SimpleMesh();
		SimpleMesh steakCooking = new SimpleMesh();

		SimpleMesh table = new SimpleMesh();
		SimpleMesh chair = new SimpleMesh();
		SimpleMesh machine = new SimpleMesh();
		SimpleMesh monitor = new SimpleMesh();
		fridge = new AnimatedMesh();
		SimpleMesh oven = new SimpleMesh();
		table.load("mesh.furniture.table");
		chair.load("mesh.furniture.chair");
		machine.load("mesh.furniture.order_machine");
		monitor.load("mesh.furniture.monitor");
		fridge.load("mesh.furniture.fridge");
		oven.load("mesh.furniture.oven");
		steak.load("mesh.furniture.steak");
		steakRaw.load("mesh.furniture.steak_raw");
		steakCooking.load("mesh.furniture.steak_cooking");
		world.addChild(table);
		world.addChild(chair);
		world.addChild(machine);
		world.addChild(monitor);
		world.addChild(fridge);
		world.addChild(oven);
		world.addChild(steak);
		world.addChild(steakCooking);
		world.addChild(steakRaw);

		table.setPosition(true, 0, 0, 0);
		chair.setPosition(true, 40, 0, 0);
		machine.setPosition(true, 0, 50, 0);
		monitor.setPosition(true, 0, 100, 0);
		fridge.setPosition(true, 0, 150, 0);
		oven.setPosition(true, 0, 220, 0);

		steak.setPosition(true, 0, 0, 0);
		steakRaw.setPosition(true, 15, 0, 0);
		steakCooking.setPosition(true, 30, 0, 0);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (KeyboardData.data.wasPressed(Keyboard.KEY_F))
		{
			fridge.setAnimation("open");
		}

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
		return new GameNetworkController(characterHandler);
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
		player = new GameObject();

		player.addBehavior(new FreeFlyBehavior());
		player.addBehavior(new CameraUpdateViewBehavior());
		player.addBehavior(new OnlinePositionSender());
		
		gameObjects.add(player);
	}
}
