package zyx.game.controls.input;

import zyx.game.controls.input.scheme.InputScheme;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zyx.engine.utils.ScreenSize;
import zyx.game.controls.input.scheme.InputSchemeMap;
import zyx.utils.interfaces.IUpdateable;

public class InputManager implements IUpdateable
{

	private static final InputManager INSTANCE = new InputManager();

	private final MouseData mouseData;
	private final KeyboardData keyboardData;

	public final InputScheme scheme;

	private InputManager()
	{
		mouseData = MouseData.data;
		keyboardData = KeyboardData.data;
		scheme = new InputScheme(InputSchemeMap.WASD);
	}

	public static InputManager getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		mouseData.reset();
		keyboardData.reset();
		scheme.reset();

		checkMouse();
		checkKeyboard();
	}

	private void checkMouse()
	{
		mouseData.grabbed = Mouse.isGrabbed();

		int combinedX = 0;
		int combinedY = 0;

		int lastX = mouseData.x;
		int lastY = mouseData.y;

		while (Mouse.next())
		{
			//Position
			if (mouseData.grabbed)
			{
				mouseData.x = ScreenSize.windowWidth / 2;
				mouseData.y = ScreenSize.windowHeight / 2;

				combinedX += Mouse.getEventDX();
				combinedY += Mouse.getEventDY();
			}
			else
			{
				mouseData.x = Mouse.getX();
				mouseData.y = ScreenSize.windowHeight - Mouse.getY();
			}

			//Movements
			//Clicking
			int button = Mouse.getEventButton();
			if (button != -1)
			{
				boolean isDown = Mouse.getEventButtonState();
				mouseData.setClickData(button, isDown);
			}
		}

		//Movements
		if (mouseData.grabbed)
		{
			mouseData.dX = combinedX;
			mouseData.dY = combinedY;
		}
		else
		{
			mouseData.dX = lastX - mouseData.x;
			mouseData.dY = lastY - mouseData.y;
		}

		scheme.mouseDx = mouseData.dX;
		scheme.mouseDy = mouseData.dY;
	}

	private void checkKeyboard()
	{
		while (Keyboard.next())
		{
			int key = Keyboard.getEventKey();
			boolean isDown = Keyboard.getEventKeyState();

			keyboardData.setClickData(key, isDown);
		}

		scheme.tryMap(keyboardData);
	}
}
