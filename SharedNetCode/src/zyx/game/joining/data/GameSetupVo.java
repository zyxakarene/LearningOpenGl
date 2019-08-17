package zyx.game.joining.data;

public class GameSetupVo
{

	public static final GameSetupVo INSTANCE = new GameSetupVo();

	public GameSetupPlayerInfo[] players;

	private GameSetupVo()
	{
	}
}
