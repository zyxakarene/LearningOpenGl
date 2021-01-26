package zyx.game.controls.input;

abstract class AbstractInputData
{
	private static final int PREV_KEY_MAX = 15;
	
	public boolean[] btnDown;
	public boolean[] btnClicked;
	
	private final int maxId;
	
	private int[] prevKeys;
	private int prevKeyCount;

	protected AbstractInputData(int count)
	{
		btnDown = new boolean[count];
		btnClicked = new boolean[count];
		
		maxId = count - 1;
		
		prevKeys = new int[PREV_KEY_MAX];
		prevKeyCount = 0;
	}

	void reset()
	{
		for (int i = 0; i < prevKeyCount; i++)
		{
			int prevKey = prevKeys[i];
			btnClicked[prevKey] = false;
		}
		
		prevKeyCount = 0;
	}

	void setClickData(int id, boolean isDown)
	{
		if (id > maxId)
		{
			return;
		}
		
		if (prevKeyCount < PREV_KEY_MAX)
		{
			boolean wasDown = btnDown[id];

			btnClicked[id] = !wasDown && isDown;
			btnDown[id] = isDown;
		
			prevKeys[prevKeyCount] = id;
			prevKeyCount++;
		}
	}
	
	public boolean isDown(int id)
	{
		return btnDown[id];
	}
	
	public boolean wasPressed(int id)
	{
		return btnClicked[id];
	}
}
