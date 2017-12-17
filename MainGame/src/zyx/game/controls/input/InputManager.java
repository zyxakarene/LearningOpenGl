package zyx.game.controls.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.utils.GameConstants;
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

	private InputManager()
	{
		onMouseMoved = new CustomCallback<>();
		OnMouseDownClicked = new CustomCallback<>();
		OnMouseUpClicked = new CustomCallback<>();
		OnMouseClicked = new CustomCallback<>();

		mouseData = MouseData.instance;
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
		while (Mouse.next())
		{
			//Position
			mouseData.x = Mouse.getX();
			mouseData.y = GameConstants.GAME_HEIGHT - Mouse.getY();

			//Movements
			mouseData.dX = Mouse.getEventDX();
			mouseData.dY = Mouse.getEventDY();

			//Clicking
			int button = Mouse.getEventButton();
			if (button != -1)
			{
				boolean isDown = Mouse.getEventButtonState();
				mouseData.setClickData(button, isDown);
			}
		}
	}

	private void checkKeyboard()
	{
		while (Keyboard.next())
		{			
			int key = Keyboard.getEventKey();
			boolean isDown = Keyboard.getEventKeyState();
			
			keyboardData.setClickData(key, isDown);
		}
	}
}
