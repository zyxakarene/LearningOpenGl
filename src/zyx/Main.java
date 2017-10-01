package zyx;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.game.components.WorldObject;
import zyx.game.components.screen.DisplayObject;
import zyx.game.components.screen.DisplayObjectContainer;
import zyx.game.components.screen.Image;
import zyx.game.components.screen.Stage;
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FPSCounter;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class Main
{

	private static Camera camera;
	private static WorldObject robot;

	private static Stage stage;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1);

		GLUtils.enableGLSettings();

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
		ShaderManager.INSTANCE.initialize();

		camera = new Camera();
		robot = new WorldObject();
		stage = Stage.instance;

		DisplayObjectContainer container = new DisplayObjectContainer();
		Image image = new Image("sample");

		container.addChild(image);
		stage.addChild(container);

		container.position.x = 10;
		container.rotation = 45;
		image.position.x = 10;
	}

}
