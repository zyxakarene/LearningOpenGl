package zyx.game.controls.input;

import org.lwjgl.input.Keyboard;

public final class KeyboardData extends AbstractInputData
{

	public final static KeyboardData data = new KeyboardData();

	private KeyboardData()
	{
		super(Keyboard.getKeyCount());
	}
}
