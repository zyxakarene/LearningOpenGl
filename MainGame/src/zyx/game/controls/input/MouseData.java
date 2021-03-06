package zyx.game.controls.input;

public final class MouseData extends AbstractInputData
{

	public static final int INDEX_LEFT = 0;
	public static final int INDEX_RIGHT = 1;
	public static final int INDEX_MIDDLE = 2;
	public static final int INDEX_PREV = 3;
	public static final int INDEX_NEXT = 4;

	public final static MouseData data = new MouseData();

	public int x;
	public int y;
	public int dX;
	public int dY;
	public boolean grabbed;

	private MouseData()
	{
		super(5);
	}

	@Override
	void reset()
	{
		super.reset();
		
		dX = 0;
		dY = 0;
	}

	public boolean isLeftDown()
	{
		return btnDown[INDEX_LEFT];
	}
	
	public boolean isLeftClicked()
	{
		return btnClicked[INDEX_LEFT];
	}

	public boolean isRightDown()
	{
		return btnDown[INDEX_RIGHT];
	}
	
	public boolean isRightClicked()
	{
		return btnClicked[INDEX_RIGHT];
	}
}
