package zyx.game.joining.data;

public class GameSetupVo
{

	public static final GameSetupVo INSTANCE = new GameSetupVo();

	public final CharacterJoinedData players;
	public final FurnitureSetupData furniture;
	public final ItemSetupData items;

	private GameSetupVo()
	{
		players = CharacterJoinedData.INSTANCE;
		furniture = FurnitureSetupData.INSTANCE;
		items = ItemSetupData.INSTANCE;
	}
}
