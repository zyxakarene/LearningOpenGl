package zyx.game.joining.data;

public class GameSetupVo
{

	public static final GameSetupVo INSTANCE = new GameSetupVo();

	public final CharacterJoinedData players;

	private GameSetupVo()
	{
		players = CharacterJoinedData.INSTANCE;
	}
}
