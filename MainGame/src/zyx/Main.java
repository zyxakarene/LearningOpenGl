package zyx;

import java.io.File;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.game.components.WorldObject;
import zyx.game.components.screen.DisplayObjectContainer;
import zyx.game.components.screen.Image;
import zyx.game.components.screen.Stage;
import zyx.game.components.screen.Textfield;
import zyx.game.components.world.camera.CameraController;
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.game.controls.SharedPools;
import zyx.game.controls.models.ModelManager;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.GameConstants;

public class Main
{

	private static CameraController camera;
	private static WorldObject platform;
	private static WorldObject mainKnight;
	private static WorldObject attachedKnight1;

	private static Stage stage;
	private static BitmapFont bmpFont;
	private static Textfield field;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.60f, 0.80f, 0.92f, 1);

		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();

		ResourceLoader.getInstance().addThreads(3);

		loadFontLogic();
		load();

		GLUtils.errorCheck();

		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			ResourceLoader.getInstance().handleResourceReplies();

			update();
			draw();

			FPSCounter.updateFPS();

			GLUtils.errorCheck();

			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_1) && mainKnight == null)
			{
				mainKnight = new WorldObject();
				attachedKnight1 = new WorldObject();

				mainKnight.load("assets/models/knight/knight.zaf");
				attachedKnight1.load("assets/models/knight/knight.zaf");
				mainKnight.setAnimation("attack");
				attachedKnight1.setAnimation("attack");
				
				mainKnight.addAttachment(attachedKnight1, "Skeleton_Hand_R");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_2))
			{
				dispose();
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_3))
			{
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_4))
			{
			}

			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_ESCAPE))
			{
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
		
		ModelManager.getInstance().dispose();
//		SharedPools.MATRIX_POOL.dispose();
//		SharedPools.VECTOR_POOL.dispose();
//		SharedPools.QUARERNION_POOL.dispose();
	}
	
	private static void update()
	{
		KeyboardControl.checkKeys();
		MouseControl.check();

		DeltaTime.update();

		int elapsed = DeltaTime.getElapsedTime();
		long timestamp = DeltaTime.getTimestamp();

		camera.update(timestamp, elapsed);
		if (mainKnight != null)
		{
			mainKnight.update(timestamp, elapsed);
		}
		platform.update(timestamp, elapsed);
//		dummyObject.update(timestamp, elapsed);
//		object3.update(timestamp, elapsed);
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (mainKnight != null)
		{
			mainKnight.draw();
		}
		
		platform.draw();
//		dummyObject.draw();
//		object3.draw();

		GLUtils.disableDepthTest();
		stage.drawStage();
		GLUtils.enableDepthTest();
	}

	private static void load()
	{
		Camera.getInstance().initialize();

		camera = new CameraController();
//		mainKnight = new WorldObject("invalid");
//		attachedKnight1 = new WorldObject("invalid");
//
//		mainKnight.load("assets/models/knight.zaf");
//		attachedKnight1.load("assets/models/knight.zaf");
//		mainKnight.setAnimation("attack");
//		attachedKnight1.setAnimation("attack");

		platform = new WorldObject();
		platform.load("assets/models/platform.zaf");

		DisplayObjectContainer container = new DisplayObjectContainer();
		Image image = new Image("sample.png");

		container.addChild(image);
		stage = Stage.instance;
		stage.addChild(container);

		container.position.x = 50;
		container.position.y = 500;
//		container.rotation = 45;
//		image.position.x = 10;

		field = new Textfield(bmpFont);
		field.scale.set(0.2f, 0.2f);
		field.setText("Testing bitmap fonts");
		field.position.x = GameConstants.GAME_WIDTH / 4;
		field.position.y = GameConstants.GAME_HEIGHT / 4;

//		stage.addChild(field);
	}

	private static void loadFontLogic()
	{
		try
		{
			File file = new File("assets/fonts/font.zff");

			BitmapFontGenerator gen = new BitmapFontGenerator(TextureManager.getFontTexture("font"));

			gen.loadFromFnt(file);
			bmpFont = gen.createBitmapFont();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

}
