package zyx.server.world.interactable.common.player;

import zyx.server.world.humanoids.handheld.HandheldItem;

public class PlayerInteraction
{
	public final PlayerInteractionType type;
	
	public final int itemIdToTake;
	public final HandheldItem itemToGive;

	private PlayerInteraction(int itemIdToTake)
	{
		this.type = PlayerInteractionType.TAKE;
		this.itemIdToTake = itemIdToTake;
		
		this.itemToGive = null;
	}

	private PlayerInteraction(HandheldItem itemToGive)
	{
		this.type = PlayerInteractionType.GIVE;
		this.itemToGive = itemToGive;
		
		this.itemIdToTake = -1;
	}
	
	public static PlayerInteraction give(HandheldItem item)
	{
		return new PlayerInteraction(item);
	}
	
	public static PlayerInteraction take(int itemId)
	{
		return new PlayerInteraction(itemId);
	}
	
	public boolean isGive()
	{
		return type == PlayerInteractionType.GIVE && itemToGive != null;
	}
	
	public boolean isTake()
	{
		return type == PlayerInteractionType.TAKE && itemIdToTake != -1;
	}
}
