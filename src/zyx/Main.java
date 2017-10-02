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
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;
import zyx.utils.FPSCounter;
import zyx.utils.GameConstants;

public class Main
{

	private static Camera camera;
	private static WorldObject robot;

	private static Stage stage;
	private static BitmapFont bmpFont;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);

		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();

		loadFontLogic();
		load();

		GLUtils.errorCheck();

//		char a = 'a';
//		char b = 'b';
//		
//		char combo = (char) ((b << 7) + a);
//		
//		char readB = (char) (combo >> 7);
//		char readA = (char) (combo & 0xFF);
//		
//		System.out.println(Integer.toBinaryString(a));
//		System.out.println(Integer.toBinaryString(b));
//		
//		System.out.println(Integer.toBinaryString(combo));
//		System.out.println(combo);
//		
//		System.out.println(readA);
//		System.out.println(readB);
				
		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			update();
			draw();

			FPSCounter.updateFPS();

			GLUtils.errorCheck();

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

		camera.update(16);
		robot.update(16);
	}

	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		robot.draw();
		stage.drawStage();
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

		Textfield field = new Textfield(bmpFont);
		field.setText("FF..FF..FF..FF.");
		field.position.x = 200;
		field.position.y = 200;
		field.scale.set(1, 1);
		
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
