package zyx;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import zyx.engine.components.screen.*;
import zyx.game.components.GameObject;
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.game.components.screen.AddBitmapFontButton;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.MegaManager;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.sound.SoundManager;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.GameConstants;

public class Main
{

	private static CameraController camera;
	private static GameObject platform;
	private static GameObject mainKnight;
	private static GameObject attachedKnight1;

	private static Stage stage;
	private static World3D world;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.60f, 0.80f, 0.92f, 1);

		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();

		ResourceLoader.getInstance().addThreads(3);

		load();

		GLUtils.errorCheck();

		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			update();
			draw();

			FPSCounter.updateFPS();

			GLUtils.errorCheck();

			if (KeyboardData.data.wasPressed(Keyboard.KEY_1) && mainKnight == null)
			{
				mainKnight = new GameObject();
				attachedKnight1 = new GameObject();
				
				platform.addChild(mainKnight);
				
				mainKnight.load("assets/models/knight/knight.zaf");
				attachedKnight1.load("assets/models/knight/knight.zaf");
				mainKnight.setAnimation("attack");
				attachedKnight1.setAnimation("attack");
				
				mainKnight.addAttachment(attachedKnight1, "Skeleton_Hand_R");
				
//				world.addChild(mainKnight);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_2))
			{
				dispose();
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_3))
			{
				SoundManager.getInstance().playSound("assets/sounds/Pacman.wav", mainKnight);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_4))
			{
				CursorManager.getInstance().setCursor(GameCursor.HAND);
			}
			if (KeyboardData.data.wasPressed(Keyboard.KEY_5))
			{
				CursorManager.getInstance().setCursor(GameCursor.POINTER);
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

		camera.update(timestamp, elapsed);
		
		if (mainKnight != null)
		{
//			mainKnight.setRotZ(mainKnight.getRotZ() + 1f);
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
		CursorManager.getInstance().initialize();
		Camera.getInstance().initialize();

		camera = new CameraController();

		platform = new GameObject();
		platform.load("assets/models/platform.zaf");

		DisplayObjectContainer container = new DisplayObjectContainer();
		Image image = new Image();
		image.load("assets/textures/sample.png");
		image.position.x = 50;
		image.position.y = 30;
		Image image2 = new Image();
		image2.load("assets/textures/sample.png");
		image2.position.x = 530;
		image2.position.y = 500;

		container.addChild(image);
		stage = Stage.instance;
		stage.addChild(container);
		
		world = World3D.instance;
		world.addChild(platform);

		container.position.x = 500;
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
	}

}
