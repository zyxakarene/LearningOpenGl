package zyx.game.controls.input.scheme;

import org.lwjgl.input.Keyboard;

public class InputSchemeMap
{

	public static final InputSchemeMap WASD = new InputSchemeMap();

	static
	{
		WASD.forward = Keyboard.KEY_W;
		WASD.backward = Keyboard.KEY_S;
		WASD.left = Keyboard.KEY_A;
		WASD.right = Keyboard.KEY_D;

		WASD.toggleClipboard = Keyboard.KEY_SPACE;
		WASD.use = Keyboard.KEY_E;
		WASD.sprint = Keyboard.KEY_LSHIFT;
		WASD.walk = Keyboard.KEY_LCONTROL;
	}

	public int forward;
	public int backward;
	public int left;
	public int right;

	public int toggleClipboard;
	public int use;
	public int sprint;
	public int walk;

	private InputSchemeMap()
	{
	}
}
