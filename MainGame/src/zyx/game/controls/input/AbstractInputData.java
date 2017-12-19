package zyx.game.controls.input;

import java.util.Arrays;

abstract class AbstractInputData
{

	public boolean[] btnDown;
	public boolean[] btnClicked;
	
	private final int maxId;

	protected AbstractInputData(int count)
	{
		btnDown = new boolean[count];
		btnClicked = new boolean[count];
		
		maxId = count - 1;
	}

	void reset()
	{
		Arrays.fill(btnClicked, false);
	}

	void setClickData(int id, boolean isDown)
	{
		if (id > maxId)
		{
			return;
		}
		
		boolean wasDown = btnDown[id];

		btnClicked[id] = wasDown && !isDown;
		btnDown[id] = isDown;
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
