package zyx.opengl;

import java.io.File;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.Msg;
import zyx.utils.exceptions.UncaughtExceptionHandlerImpl;
import zyx.utils.interfaces.ICommand;

public class SetupOpenGlCommand implements ICommand
{

	private static final boolean DEBUG = false;

	@Override
	public void execute()
	{
		try
		{
			System.setProperty("java.library.path", "lib");
			System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());

			setupLogging();

			setupLwjgl();
		}
		catch (LWJGLException | IOException ex)
		{
			Msg.error("Fatal exception in SetupCommand", ex);
			System.exit(-1);
		}
	}

	private void setupLwjgl() throws LWJGLException
	{
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAtrributes;
		if (DEBUG)
		{
			contextAtrributes = new ContextAttribs(4, 3);
			contextAtrributes.withDebug(true);
		}
		else
		{
			contextAtrributes = new ContextAttribs(3, 2);
		}
		contextAtrributes.withForwardCompatible(true);
		contextAtrributes.withProfileCore(true);

		Display.setDisplayMode(new DisplayMode(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT));
		Display.create(pixelFormat, contextAtrributes);

		if (DEBUG)
		{
			KHRDebugCallback callback = new KHRDebugCallback();
			GL43.glDebugMessageCallback(callback);
		}

		Keyboard.create();
	}

	private void setupLogging() throws IOException
	{
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerImpl());
	}
}
