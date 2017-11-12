package zyx;

import dev.bones.MeshControl;
import dev.bones.SnakeControl;
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
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class Main
{

	private static Camera camera;
	private static WorldObject robot;

	private static Stage stage;
	private static BitmapFont bmpFont;
	private static Textfield field;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);

		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();

		loadFontLogic();
		load();

		GLUtils.errorCheck();
		
		new SnakeControl().setVisible(true);

		float time = 0;
		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			update();
			draw();

			FPSCounter.updateFPS();

			GLUtils.errorCheck();

			if (KeyboardControl.wasKeyPressed(Keyboard.KEY_1))
			{
				field.rotation += 5;
				time += 0.1f;
				field.scale.set(FloatMath.sin(time) + 0.5f, FloatMath.sin(time) + 0.5f);

				field.position.x = (GameConstants.GAME_WIDTH / 2) + FloatMath.cos(time) * 100f;
				field.position.y = (GameConstants.GAME_HEIGHT / 2) + FloatMath.sin(time) * 100f;
				
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
		robot.update(timestamp, elapsed);
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		robot.draw();

		GLUtils.disableDepthTest();
		stage.drawStage();
		GLUtils.enableDepthTest();
	}

	private static void load()
	{
		camera = new Camera();
		robot = new WorldObject();
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
