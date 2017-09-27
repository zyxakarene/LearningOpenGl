package zyx;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.game.components.WorldObject;
import zyx.game.components.screen.DisplayObject;
import zyx.game.components.screen.DisplayObjectContainer;
import zyx.game.components.screen.Image;
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
	private static WorldObject object;
	
	private static DisplayObjectContainer disp;
	

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
		disp.draw();
	}

	private static void load()
	{
		ShaderManager.INSTANCE.initialize();

		camera = new Camera();
		
		object = new WorldObject();
		
		disp = new DisplayObjectContainer();
		disp.position.x = 10;
		
		Image img = new Image("sample");
		
		disp.addChild(img);
		
	}

}
