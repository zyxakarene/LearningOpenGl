package zyx.game.controls.input;

import java.util.Arrays;

abstract class AbstractInputData
{

	public boolean[] btnDown;
	public boolean[] btnClicked;

	protected AbstractInputData(int count)
	{
		btnDown = new boolean[count];
		btnClicked = new boolean[count];
	}

	void reset()
	{
		Arrays.fill(btnClicked, false);
	}

	void setClickData(int id, boolean isDown)
	{
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
