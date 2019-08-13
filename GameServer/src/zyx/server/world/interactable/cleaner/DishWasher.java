package zyx.server.world.interactable.cleaner;

import java.awt.Color;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.cleaner.CleanerBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.common.player.IPlayerInteractable;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.common.useable.UseableItem;

public class DishWasher extends UseableItem<Cleaner> implements IPlayerInteractable
{
	private static final int WASHING_TIME = 300;
	
	public DishWasher()
	{
		super(WASHING_TIME);
	}

	@Override
	public void interactWith(Cleaner cleaner)
	{
		inUse = true;
		startUsing(cleaner);
	}

	@Override
	public void interactWith(Player player, PlayerInteraction interaction)
	{
		if (!inUse)
		{
			startUsing(player);
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		inUse = false;
		if (currentUser != null)
		{
			currentUser.removeItem();
			currentUser.requestBehavior(CleanerBehaviorType.IDLE);
		}
	}

	@Override
	public Color getColor()
	{
		return Color.ORANGE;
	}
}
