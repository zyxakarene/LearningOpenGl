package zyx.game.world.player.data;

public class PlayerRequestData
{
	public static final PlayerRequestData INSTANCE = new PlayerRequestData();
	
	public int npcId;
	public int itemId;
	public int typeId;
	public int playerId;
	public int ownerId;

	private PlayerRequestData()
	{
	}
}
