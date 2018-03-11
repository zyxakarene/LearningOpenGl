package zyx;

import zyx.game.components.world.camera.FirstPersonBehavior;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.*;
import zyx.game.components.GameObject;
import zyx.engine.components.world.World3D;
import zyx.engine.components.world.physics.BoxCollider;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.worldpicker.WorldPicker;
import zyx.engine.utils.worldpicker.calculating.PhysPicker;
import zyx.engine.utils.worldpicker.calculating.PhysPlanePicker;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.game.behavior.BehaviorType;
import zyx.game.components.screen.AddBitmapFontButton;
import zyx.game.components.world.player.Player;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.sound.SoundManager;
import zyx.net.io.ConnectionLoader;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.textures.ColorTexture;
import zyx.opengl.textures.RenderTexture;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.DebugContainer;
import zyx.utils.cheats.DebugPhysics;
import zyx.utils.cheats.DebugPoint;

public class Main
{

	private static WorldPicker picker;
	private static RenderTexture ren;

	private static Player player;
	private static DebugContainer debugContainer;

	private static CameraController camera;
	private static GameObject ground;
	private static GameObject boxTv;
	private static GameObject platform;
	private static GameObject mainKnight;
	private static GameObject attachedKnight1;
	private static GameObject teapot;

	private static Stage stage;
	private static World3D world;
	private static Vector3f cameraPos = new Vector3f();
	private static Vector3f cameraRot = new Vector3f();
	private static Vector3f cameraPosOrig = new Vector3f();
	private static Vector3f cameraRotOrig = new Vector3f();

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.60f, 0.80f, 0.92f, 1);

		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();

		ResourceLoader.getInstance().addThreads(1);
		ConnectionLoader.getInstance().connect("localhost", 8888);
		ConnectionLoader.getInstance().startThreads();

		picker = new WorldPicker();
		load();


		GLUtils.errorCheck();

		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			CursorManager.getInstance().setCursor(GameCursor.POINTER);
			update();
			CursorManager.getInstance().update();
			world.updateMatrix();

			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ren.bufferId);
			GL11.glViewport(0, 0, (int) ren.getWidth(), (int) ren.getHeight());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			if (mainKnight != null)
			{
				camera.getPosition(true, cameraPosOrig);
				camera.getRotation(true, cameraRotOrig);

				camera.setPosition(cameraPos);
				camera.setRotation(cameraRot);
				camera.getBehaviorById(BehaviorType.CAMERA_UPDATE_VIEW).update(0, 0);
				world.drawScene();

				camera.setPosition(cameraPosOrig);
				camera.setRotation(cameraRotOrig);
				camera.getBehaviorById(BehaviorType.CAMERA_UPDATE_VIEW).update(0, 0);
			}

			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
			GL11.glViewport(0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
			draw();

			GLUtils.errorCheck();

			if (KeyboardData.data.wasPressed(Keyboard.KEY_1) && mainKnight == null)
			{
				mainKnight = new GameObject();
				attachedKnight1 = new GameObject();

				platform.addChild(mainKnight);
				boxTv.setTexture(ren);

				mainKnight.load("assets/models/knight/knight.zaf");
				attachedKnight1.load("assets/models/knight/knight.zaf");
				mainKnight.setAnimation("attack");
				attachedKnight1.setAnimation("attack");

				mainKnight.addAttachment(attachedKnight1, "Skeleton_Hand_R");

//				world.addChild(mainKnight);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_2))
			{
				boxTv.setCollider(new BoxCollider(40, 40, 40, true));
//				dispose();
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_3))
			{
				SoundManager.getInstance().playSound("assets/sounds/Pacman.wav", mainKnight);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_4))
			{
				camera.getPosition(true, cameraPos);
				camera.getRotation(true, cameraRot);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_5))
			{
				Vector3f pos = camera.getPosition(true, null);
				DebugPoint.addToScene(-pos.x, -pos.y, -pos.z, 10000);
			}

			if (KeyboardData.data.wasPressed(Keyboard.KEY_ESCAPE))
			{
				SoundManager.getInstance().dispose();

				Display.destroy();
				Keyboard.destroy();
				Mouse.destroy();
				System.exit(0);
			}
		}
	}

	private static void dispose()
	{
		if (mainKnight != null)
		{
			mainKnight.dispose();
			mainKnight = null;
		}

//		ModelManager.getInstance().dispose();
//		SharedPools.MATRIX_POOL.dispose();
//		SharedPools.VECTOR_POOL.dispose();
//		SharedPools.QUARERNION_POOL.dispose();
	}

	private static void update()
	{
		DeltaTime.update();
		long timestamp = DeltaTime.getTimestamp();
		int elapsed = DeltaTime.getElapsedTime();

		MegaManager.update(timestamp, elapsed);
		picker.update();

		camera.update(timestamp, elapsed);
		player.update(timestamp, elapsed);
		debugContainer.update(timestamp, elapsed);

		FPSCounter.updateFPS();

		world.physics.update(timestamp, elapsed);

		if (mainKnight != null)
		{
			mainKnight.setRotZ(mainKnight.getRotZ() + 0.5f);
		}
		if (teapot != null && MouseData.instance.isRightDown())
		{
			Vector3f rot = teapot.getRotation(true, null);
			rot.x += 0.5f;
			rot.y += 0.5f;
			rot.z += 0.5f;
			teapot.setRotation(rot);
			
			Vector3f scale = teapot.getScale(true, null);
			scale.x = FloatMath.abs(FloatMath.sin(timestamp * 0.001f));
			scale.y = FloatMath.abs(FloatMath.cos(timestamp * 0.001f));
			scale.z = FloatMath.abs(FloatMath.tan(timestamp * 0.001f));
			teapot.setScale(scale);
		}
		
		if (MouseData.instance.isLeftDown())
		{
			Vector3f ray = RayPicker.getInstance().getRay();
			Vector3f out = new Vector3f();
			Vector3f pos = new Vector3f();
			camera.getPosition(false, pos);
			
			out.set(pos);
			out.x += ray.x * 100;
			out.y += ray.y * 100;
			out.z += ray.z * 100;
//			DebugPoint.addToScene(out.x, out.y, out.z, 10000);
		}
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		world.drawScene();

		GLUtils.disableDepthTest();
		GLUtils.disableCulling();
		stage.drawStage();
		GLUtils.enableCulling();
		GLUtils.enableDepthTest();

	}

	private static void load()
	{
		ren = new RenderTexture(512, 512);

		CursorManager.getInstance().initialize();
		Camera.getInstance().initialize();

		player = new Player();
		player.setPosition(0, 0, 0);

		camera = new CameraController();
		camera.addBehavior(new FirstPersonBehavior(player));

		platform = new GameObject();
		platform.setY(100);
		platform.setZ(-50);
		platform.load("assets/models/platform.zaf");

		ground = new GameObject();
		ground.setX(-100);
		ground.setZ(-100);
		ground.load("assets/models/box.zaf");
		ground.setScale(10, 10, 1);
		ground.setCollider(new BoxCollider(400, 400, 40));

		boxTv = new GameObject();
		boxTv.setX(-100);
		boxTv.setZ(-60);
		boxTv.load("assets/models/tv.zaf");
		boxTv.setCollider(new BoxCollider(40, 40, 40, true));

		DisplayObjectContainer container = new DisplayObjectContainer();
		Image image = new Image();
		image.load("assets/textures/sample.png");
		image.position.x = 50;
		image.position.y = 30;
		Image image2 = new Image();
		image2.load("assets/textures/sample.png");
		image2.position.x = 80;
		image2.position.y = 500;

		container.addChild(image);
		stage = Stage.instance;
		stage.addChild(container);

		debugContainer = new DebugContainer();

		world = World3D.instance;
		world.addChild(debugContainer);
		world.addChild(platform);
		world.addChild(boxTv);
		world.addChild(ground);
		world.addChild(player);

		addRandomBoxes();

		container.position.x = 50;
		container.position.y = 500;
		container.rotation = 45;
//		container.rotation = 45;
//		image.position.x = 10;

		stage.addChild(image2);

		AddBitmapFontButton btn = new AddBitmapFontButton("assets/textures/BtnUp.png", "assets/textures/BtnHover.png", "assets/textures/BtnDown.png");
		btn.position.set(100, 200);
		stage.addChild(btn);

		Checkbox checkbox = new Checkbox("assets/textures/BtnUp.png", "assets/textures/BtnHover.png", "assets/textures/BtnDown.png", "assets/textures/Check.png");
		checkbox.position.set(125, 220);
		stage.addChild(checkbox);

		teapot = new GameObject();
		teapot.setZ(-50);
		teapot.load("assets/models/teapot.zaf");
		teapot.registerClick(new OnTeaPotClicked());
		world.addChild(teapot);
		
		picker.addObject(teapot);
	}

	private static void addRandomBoxes()
	{
		for (int i = 0; i < 20 * 0; i++)
		{
			float scaleX = FloatMath.random() * 3;
			float scaleY = FloatMath.random() * 3;
			float scaleZ = FloatMath.random() * 3;

			GameObject box = new GameObject();
			box.setX(FloatMath.random() * -200f);
			box.setY(FloatMath.random() * -200f);
			box.setZ((FloatMath.random() * 200f) - 50);
			box.setScale(scaleX, scaleY, scaleZ);
			box.load("assets/models/box.zaf");
			box.setCollider(new BoxCollider(40 * scaleX, 40 * scaleY, 40 * scaleZ));
			box.registerClick(new OnTeaPotClicked());
			picker.addObject(box);
			
			world.addChild(box);
		}
	}
}
