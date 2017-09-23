package zyx;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.game.components.WorldObject;
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.GLErrors;

public class Main
{

	private static Camera camera;
	private static WorldObject object;

	public static void main(String[] args)
	{
		new SetupOpenGlCommand().execute();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		load();

		GLErrors.errorCheck();

		while (!Display.isCloseRequested())
		{
			Display.update();
			Display.sync(GameConstants.FPS);

			update();
			draw();

			GLErrors.errorCheck();
		}
	}

	private static void update()
	{
		KeyboardControl.checkKeys();
		MouseControl.check();
		
		camera.update(16);
		object.update(16);
	}
	
	private static void draw()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		object.draw();
	}

	private static void load()
	{
		ShaderManager.INSTANCE.initialize();
		ShaderManager.INSTANCE.bind(Shader.WORLD);

		camera = new Camera();
		
		object = new WorldObject();
	}

}
