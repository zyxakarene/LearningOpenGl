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
	private static WorldObject mainKnight;
	private static WorldObject attachedKnight1;
	private static WorldObject attachedKnight2;
	private static WorldObject attachedKnight3;
	private static WorldObject attachedKnight4;

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
				mainKnight.addAttachment(attachedKnight1, "Skeleton_Hand_R");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_2))
			{
				attachedKnight1.addAttachment(attachedKnight2, "Skeleton_Hand_R");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_3))
			{
				attachedKnight2.addAttachment(attachedKnight3, "Skeleton_Hand_R");
			}
			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_4))
			{
				attachedKnight3.addAttachment(attachedKnight4, "Skeleton_Hand_R");
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
		mainKnight.update(timestamp, elapsed);
//		dummyObject.update(timestamp, elapsed);
//		object3.update(timestamp, elapsed);
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		mainKnight.draw();
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
		mainKnight = new WorldObject("invalid");
		attachedKnight1 = new WorldObject("invalid");
		attachedKnight2 = new WorldObject("invalid");
		attachedKnight3 = new WorldObject("invalid");
		attachedKnight4 = new WorldObject("invalid");
		stage = Stage.instance;

		mainKnight.load("assets/models/knight.zaf");
		attachedKnight1.load("assets/models/knight.zaf");
		attachedKnight2.load("assets/models/knight.zaf");
		attachedKnight3.load("assets/models/knight.zaf");
		attachedKnight4.load("assets/models/knight.zaf");
		mainKnight.setAnimation("attack");
		attachedKnight1.setAnimation("attack");
		attachedKnight2.setAnimation("attack");
		attachedKnight3.setAnimation("attack");
		attachedKnight4.setAnimation("attack");

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
