package zyx.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import zyx.debug.DebugController;
import zyx.debug.views.DebugFrame; 
import zyx.engine.components.world.World3D;
import zyx.engine.curser.CursorManager;
import zyx.engine.scene.SceneManager;
import zyx.engine.sound.SoundSystem;
import zyx.engine.utils.ScreenSize;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.sound.SoundManager;
import zyx.game.scene.SceneType;
import zyx.opengl.GLUtils;
import zyx.opengl.OpenGlStarter;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.ShaderManager;
import zyx.utils.DeltaTime;
import zyx.utils.FPSCounter;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;

public class GameEngine
{
	public static int drawCalls = 0;

	private SceneManager sceneManager;
	private DisplaySizeChanger sizeChanger;
	
	public GameEngine()
	{
		sceneManager = SceneManager.getInstance();
	}

	public void startWith(SceneType startScene)
	{
		OpenGlStarter.setupOpenGl();
		GLUtils.enableGLSettings();
		
		sizeChanger = new DisplaySizeChanger();
		
		ShaderManager.getInstance().initialize();
		CursorManager.getInstance().initialize();
		Camera.getInstance().initialize();

		SoundSystem.initialize();

		sceneManager.changeScene(startScene);		
		beginGameLoop();
	}

	private void beginGameLoop()
	{
		boolean running = true;
				
		while (running)
		{
			if (KeyboardData.data.wasPressed(Keyboard.KEY_R))
			{
				int width = (int) (64 + (Math.random() * 1920 * 0.75));
				int height = (int) (64 + (Math.random() * 1080 * 0.75));
				ScreenSize.changeScreenSize(width, height);
			}
			
			if (Display.wasResized())
			{
				Print.out(Display.getWidth(), Display.getHeight());
				ScreenSize.changeScreenSize(Display.getWidth(), Display.getHeight());
			}
			
			running = !Display.isCloseRequested();
			
			if (KeyboardData.data.wasPressed(Keyboard.KEY_1))
			{
				sceneManager.changeScene(SceneType.MENU);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_2))
			{
				sceneManager.changeScene(SceneType.GAME);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_3))
			{
				sceneManager.changeScene(SceneType.PARTICLE);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_4))
			{
				sceneManager.changeScene(SceneType.TEST);
			}
			else if (KeyboardData.data.wasPressed(Keyboard.KEY_5))
			{
				sceneManager.changeScene(SceneType.EMPTY);
			}

			Display.update();
			Display.sync(GameConstants.FPS);

			DeltaTime.update();
			long timestamp = DeltaTime.getTimestamp();
			int elapsed = DeltaTime.getElapsedTime();

			GLUtils.clearView();

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
