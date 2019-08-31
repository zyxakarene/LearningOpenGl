package zyx.game.controls.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public class InputManager implements IUpdateable
{

	private static final InputManager instance = new InputManager();

	private final MouseData mouseData;
	private final KeyboardData keyboardData;

	public final CustomCallback<MouseData> onMouseMoved;
	public final CustomCallback<MouseData> OnMouseDownClicked;
	public final CustomCallback<MouseData> OnMouseUpClicked;
	public final CustomCallback<MouseData> OnMouseClicked;
	public final CustomCallback<Character> OnKeyPressed;

	private InputManager()
	{
		onMouseMoved = new CustomCallback<>();
		OnMouseDownClicked = new CustomCallback<>();
		OnMouseUpClicked = new CustomCallback<>();
		OnMouseClicked = new CustomCallback<>();
		OnKeyPressed = new CustomCallback<>();

		mouseData = MouseData.data;
		keyboardData = KeyboardData.data;
	}

	public static InputManager getInstance()
	{
		return instance;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		mouseData.reset();
		keyboardData.reset();

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
				mouseData.x = ScreenSize.width / 2;
				mouseData.y = ScreenSize.height / 2;
			}
			else
			{
				mouseData.x = Mouse.getX();
				mouseData.y = ScreenSize.height - Mouse.getY();
			}

			//Movements
			combinedX += Mouse.getEventDX();
			combinedY += Mouse.getEventDY();
			
			//Clicking
			int button = Mouse.getEventButton();
			if (button != -1)
			{
				boolean wasDown = mouseData.isDown(button);
				boolean isDown = Mouse.getEventButtonState();
				mouseData.setClickData(button, isDown);

				if (!wasDown && isDown)
				{
					OnMouseDownClicked.dispatch(mouseData);
				}
				else if (wasDown && !isDown)
				{
					OnMouseUpClicked.dispatch(mouseData);
				}

				if (mouseData.wasPressed(button))
				{
					OnMouseClicked.dispatch(mouseData);
				}
			}
		}

		//Movements
		mouseData.dX = lastX - mouseData.x;
		mouseData.dY = lastY - mouseData.y;

		if (mouseData.dX != 0 || mouseData.dY != 0)
		{
			onMouseMoved.dispatch(mouseData);
		}
	}

	private void checkKeyboard()
	{
		while (Keyboard.next())
		{
			int key = Keyboard.getEventKey();
			boolean isDown = Keyboard.getEventKeyState();

			keyboardData.setClickData(key, isDown);

			char character = Keyboard.getEventCharacter();
			OnKeyPressed.dispatch(character);
		}
	}
}
