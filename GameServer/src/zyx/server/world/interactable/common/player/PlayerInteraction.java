package zyx.server.world.interactable.common.player;

public class PlayerInteraction
{
	public final PlayerInteractionType type;
	
	public final int itemIdToTake;

	private PlayerInteraction(int itemIdToTake)
	{
		this.type = PlayerInteractionType.TAKE;
		this.itemIdToTake = itemIdToTake;
	}

	private PlayerInteraction()
	{
		this.type = PlayerInteractionType.GIVE;
		
		this.itemIdToTake = -1;
	}
	
	public static PlayerInteraction give()
	{
		return new PlayerInteraction();
	}
	
	public static PlayerInteraction take(int itemId)
	{
		return new PlayerInteraction(itemId);
	}
	
	public static PlayerInteraction take()
	{
		return new PlayerInteraction(-1);
	}
	
	public boolean isGive()
	{
		return type == PlayerInteractionType.GIVE;
	}
	
	public boolean isTake()
	{
		return type == PlayerInteractionType.TAKE;
	}
}
