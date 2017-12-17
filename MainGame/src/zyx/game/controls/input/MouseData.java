package zyx.game.controls.input;

public final class MouseData extends AbstractInputData
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
}
