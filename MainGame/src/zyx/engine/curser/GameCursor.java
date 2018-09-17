package zyx.engine.curser;

public enum GameCursor
{
	TEXT("assets/cursors/text.png", 2, 7),
	POINTER("assets/cursors/pointer.png", 0, 0),
	HAND("assets/cursors/hand.png", 5, 0);

	public final String path;
	public final int x;
	public final int y;
	public final String format;

	private GameCursor(String path, int x, int y)
	{
		this.path = path;
		this.x = x;
		this.y = y;
		this.format = ".png";
	}
}
