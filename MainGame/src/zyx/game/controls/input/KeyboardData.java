package zyx.game.controls.input;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public final class KeyboardData extends AbstractInputData
{

	public final static KeyboardData data = new KeyboardData();

	public final ArrayList<Character> downKeys;
	
	private KeyboardData()
	{
		super(Keyboard.getKeyCount() * 5);
		
		downKeys = new ArrayList<>();
	}
}
