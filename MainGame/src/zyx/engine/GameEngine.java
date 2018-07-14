package zyx.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.engine.curser.CursorManager;
import zyx.engine.resources.ResourceManager;
import zyx.engine.scene.SceneManager;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.sound.SoundManager;
import zyx.game.scene.SceneType;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.ShaderManager;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.GameConstants;

public class GameEngine
{

	private SceneManager sceneManager;

	public GameEngine()
	{
		sceneManager = SceneManager.getInstance();
	}

	public void startWith(SceneType startScene)
	{
		new SetupOpenGlCommand().execute();
		GLUtils.enableGLSettings();

		ShaderManager.INSTANCE.initialize();
		CursorManager.getInstance().initialize();
		Camera.getInstance().initialize();

		sceneManager.changeScene(startScene);
		beginGameLoop();
	}

	public static int drawCalls = 0;
	
	private void beginGameLoop()
	{
		while (!Display.isCloseRequested())
		{
			if (KeyboardData.data.wasPressed(Keyboard.KEY_1))
			{
				sceneManager.changeScene(SceneType.GAME);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_2))
			{
				sceneManager.changeScene(SceneType.TEST);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_3))
			{
				sceneManager.changeScene(SceneType.PARTICLE);
			}
			
			Display.update();
			Display.sync(GameConstants.FPS);

			DeltaTime.update();
			long timestamp = DeltaTime.getTimestamp();
			int elapsed = DeltaTime.getElapsedTime();


			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			drawCalls = 0;
			sceneManager.update(timestamp, elapsed);
			FPSCounter.updateFPS();

			ResourceManager.getInstance().printData();
			
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
}
