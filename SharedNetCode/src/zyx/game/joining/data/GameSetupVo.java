package zyx.game.joining.data;

public class GameSetupVo
{

	public static final GameSetupVo INSTANCE = new GameSetupVo();

	public final CharacterJoinedData players;
	public final FurnitureSetupData furniture;

	private GameSetupVo()
	{
		players = CharacterJoinedData.INSTANCE;
		furniture = FurnitureSetupData.INSTANCE;
	}
}
