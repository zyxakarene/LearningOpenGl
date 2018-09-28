package zyx.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zyx.debug.DebugController;
import zyx.debug.views.DebugFrame;
import zyx.engine.curser.CursorManager;
import zyx.engine.scene.SceneManager;
import zyx.engine.sound.SoundSystem;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.sound.SoundManager;
import zyx.game.scene.SceneType;
import zyx.opengl.GLUtils;
import zyx.opengl.SetupOpenGlCommand;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.DebugDrawCalls;
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

		SoundSystem.initialize();

		sceneManager.changeScene(startScene);
		beginGameLoop();
	}

	public static int drawCalls = 0;

	private void beginGameLoop()
	{
		boolean running = true;
				
		while (running)
		{
			running = !Display.isCloseRequested();
			
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
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_4))
			{
				sceneManager.changeScene(SceneType.MATRIX);
			}

			Display.update();
			Display.sync(GameConstants.FPS);

			DeltaTime.update();
			long timestamp = DeltaTime.getTimestamp();
			int elapsed = DeltaTime.getElapsedTime();

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			DebugDrawCalls.reset();
			drawCalls = 0;
			sceneManager.update(timestamp, elapsed);
			FPSCounter.updateFPS();

			DebugFrame.onFrame();

			if (KeyboardData.data.wasPressed(Keyboard.KEY_ESCAPE))
			{
				running = false;
			}
		}

		close();
	}

	private void close()
	{
		SoundManager.getInstance().dispose();

		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		SoundSystem.dispose();
		
		DebugController.close();
		
		System.exit(0);
	}
}
