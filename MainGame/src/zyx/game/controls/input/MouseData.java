package zyx.game.controls.input;

import java.util.Arrays;

public final class MouseData
{

	static final int INDEX_LEFT = 0;
	static final int INDEX_RIGHT = 1;
	static final int INDEX_MIDDLE = 2;
	static final int INDEX_PREV = 3;
	static final int INDEX_NEXT = 4;

	public final static MouseData instance = new MouseData();

	public int x;
	public int y;
	public int dX;
	public int dY;

	public boolean[] btnDown;
	public boolean[] btnClicked;

	private MouseData()
	{
		btnDown = new boolean[INDEX_NEXT];
		btnClicked = new boolean[INDEX_NEXT];
	}

	void reset()
	{
		dX = 0;
		dY = 0;

		Arrays.fill(btnClicked, false);
	}

	void setClickData(int buttonId, boolean isDown)
	{
		boolean wasDown = btnDown[buttonId];

		btnClicked[buttonId] = wasDown && !isDown;
		btnDown[buttonId] = isDown;
	}
	
	public boolean isLeftDown()
	{
		return btnDown[INDEX_LEFT];
	}
	
	public boolean isLeftClicked()
	{
		return btnClicked[INDEX_LEFT];
	}
}
