package zyx.game.controls.input;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import zyx.utils.pooling.ObjectPool;

public final class KeyboardData extends AbstractInputData
{

	public final static KeyboardData data = new KeyboardData();
	
	private final ObjectPool<PressedKey> keyPool;
	
	public final List<PressedKey> pressedCharacters;
	
	private KeyboardData()
	{
		super(Keyboard.getKeyCount() * 5);
		pressedCharacters = new ArrayList<>();
		keyPool = new ObjectPool<>(PressedKey.class, 10);
	}

	@Override
	void reset()
	{
		super.reset();
		
		int len = pressedCharacters.size();
		for (int i = 0; i < len; i++)
		{
			PressedKey key = pressedCharacters.get(i);
			keyPool.releaseInstance(key);
		}
		
		pressedCharacters.clear();
	}

	void setKeyData(int id, char character)
	{
		PressedKey key = keyPool.getInstance();
		key.vk = id;
		key.character = character;
		
		pressedCharacters.add(key);
	}
}
