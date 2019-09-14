package zyx.game.scene.game;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.cubemaps.CubemapManager;
import zyx.engine.components.cubemaps.saving.CubemapProcess;
import zyx.engine.components.meshbatch.MeshBatchEntity;
import zyx.engine.components.meshbatch.MeshBatchManager;
import zyx.game.network.GameNetworkController;
import zyx.engine.components.tooltips.TestTooltip;
import zyx.engine.components.tooltips.TooltipManager;
import zyx.engine.components.world.GameLight;
import zyx.engine.components.world.WorldObject;
import zyx.engine.scene.Scene;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.behavior.BehaviorType;
import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.FreeFlyBehavior;
import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.behavior.player.OnlinePositionSender;
import zyx.game.components.GameObject;
import zyx.game.components.MeshObject;
import zyx.game.components.world.meshbatch.CubeEntity;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.process.ProcessQueue;
import zyx.game.models.GameModels;
import zyx.game.network.PingManager;
import zyx.game.vo.Gender;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.opengl.GLUtils;
import zyx.opengl.buffers.DrawingRenderer;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.opengl.models.implementations.shapes.Sphere;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;
import zyx.utils.math.QuaternionUtils;

public class DragonScene extends Scene implements ICallback<ProcessQueue>
{

	public static DragonScene current;

	private ArrayList<GameObject> gameObjects;
	private boolean cubemapping;
	private ProcessQueue processQueue;

	private MeshObject testDragon;
	private GameObject player;

	private Box drawBox;
	private IHoveredItem onBoxClick;

	public DragonScene()
	{
		gameObjects = new ArrayList<>();
		current = this;
	}

	@Override
	protected void onPreloadResources()
	{
	}

	private boolean isDrawing = false;
	private boolean changed = false;

	@Override
	protected void onInitialize()
	{
		NetworkChannel.sendRequest(NetworkCommands.LOGIN, "Zyx" + Math.random(), Gender.MALE);

		world.loadSkybox("skybox.texture.desert");
		CubemapManager.getInstance().load("cubemap.dragon");
		onBoxClick = new IHoveredItem()
		{
			@Override
			public void onClicked(ClickedInfo info)
			{
				boolean wasDrawing = isDrawing;
				isDrawing = MouseData.data.isLeftDown();

				if (!changed && isDrawing)
				{
					changed = true;
					int id = DrawingRenderer.getInstance().underlayInt();
					AbstractTexture tex = new TextureFromInt(256, 256, id, TextureSlot.SHARED_DIFFUSE);
					drawBox.setTexture(tex);
				}

				if (wasDrawing != isDrawing)
				{
					DrawingRenderer.getInstance().toggleDraw(isDrawing);
				}

				if (isDrawing)
				{
					Vector4f pos = new Vector4f(info.position.x, info.position.y, info.position.z, 1);
					Matrix4f invWorld = Matrix4f.invert(drawBox.worldMatrix(), null);
					Matrix4f.transform(invWorld, pos, pos);

					DrawingRenderer.getInstance().drawAt(pos.x, pos.z, !wasDrawing);
				}
			}
		};

		drawBox = new Box(10, 10, 10);
		drawBox.setPosition(true, 0, 0, 100);
		drawBox.setRotation(33, 24, 58);
		world.addChild(drawBox);
		addPickedObject(drawBox, onBoxClick);

		MeshObject dragon = new MeshObject();
		dragon.setScale(0.33f, 0.33f, 0.33f);
		dragon.load("mesh.dragon");
		dragon.setPosition(false, 100, 100, 100);
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
		return new GameNetworkController(itemHolderHandler, itemHandler);
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

		itemHandler.clean();
		itemHolderHandler.clean();

		gameObjects.clear();
		gameObjects = null;

		drawBox.dispose();
		removePickedObject(drawBox, onBoxClick);
		drawBox = null;

		camera.removeBehavior(BehaviorType.ONLINE_POSITION);

		PingManager.getInstance().removeEntity(GameModels.player.playerId);
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

		WorldObject box = new Box(1, 1, 1);
		WorldObject p = new Box(3f, 0.01f, 3f);
		p.setPosition(true, 0, -1f, 0);
		box.setPosition(true, 0, 0, -5);
		player.addChild(p);
		player.addChild(box);

		player.addBehavior(new FreeFlyBehavior());
		player.addBehavior(new CameraUpdateViewBehavior());
		player.addBehavior(new OnlinePositionSender());

		gameObjects.add(player);

		world.addChild(player);
	}
}
