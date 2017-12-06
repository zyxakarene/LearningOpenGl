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
	private static WorldObject object1;
	private static WorldObject object2;
	private static WorldObject object3;

	private static Stage stage;
	private static BitmapFont bmpFont;
	private static Textfield field;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);

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

			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_1))
			{
				object1.load("assets/models/knight.zaf");
				object1.setAnimation("walk");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_2))
			{
				object2.load("assets/models/knight.zaf");
				object2.setAnimation("attack");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_3))
			{
				object3.load("assets/models/knight.zaf");
				object3.setAnimation("idle");
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

	private static void update()
	{
		KeyboardControl.checkKeys();
		MouseControl.check();

		DeltaTime.update();

		int elapsed = DeltaTime.getElapsedTime();
		long timestamp = DeltaTime.getTimestamp();

		camera.update(timestamp, elapsed);
		object1.update(timestamp, elapsed);
		object2.update(timestamp, elapsed);
		object3.update(timestamp, elapsed);
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		object1.draw();
		object2.draw();
		object3.draw();

		GLUtils.disableDepthTest();
		stage.drawStage();
		GLUtils.enableDepthTest();
	}

	private static void load()
	{
		Camera.getInstance().initialize();

		camera = new CameraController();
		object1 = new WorldObject("walk");
		object2 = new WorldObject("attack");
		object3 = new WorldObject("invalid");
		object2.setX(-50);
		object3.setX(-100);
		stage = Stage.instance;

		DisplayObjectContainer container = new DisplayObjectContainer();
		Image image = new Image("sample");

		container.addChild(image);
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

		stage.addChild(field);
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
