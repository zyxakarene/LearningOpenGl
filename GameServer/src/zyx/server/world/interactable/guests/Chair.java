package zyx.server.world.interactable.guests;

import java.awt.Color;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.NpcService;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.common.player.IPlayerInteractable;

public class Chair extends GuestItem implements IPlayerInteractable
{

	private DinnerTable table;
	private boolean guestSitting;

	public Chair()
	{
		super(FurnitureType.CHAIR);
	}

	public void linkToTable(DinnerTable table)
	{
		this.table = table;
	}

	@Override
	public void makeAvailible()
	{
		super.makeAvailible();
		guestSitting = false;
		currentUser = null;
	}

	@Override
	public void interactWith(Guest guest)
	{
		if (canUse(guest))
		{
			guestSitting = true;
			currentUser.requestBehavior(GuestBehaviorType.WAITING_TO_ORDER);
		}
	}

	public boolean isCurrentGuestSitting()
	{
		return currentUser != null && guestSitting;
	}

	public Guest getCurrentGuest()
	{
		return currentUser;
	}

	@Override
	public void getUsingPosition(Vector3f pos, Vector3f lookPos)
	{
		pos.x = x;
		pos.y = y;
		pos.z = z;
		
		getDir(HELPER_DIR);
		
		lookPos.x = x + (HELPER_DIR.x * 10);
		lookPos.y = y + (HELPER_DIR.y * 10);
		lookPos.z = z + (HELPER_DIR.z * 10);
	}

	@Override
	public void interactWith(Player player, PlayerInteraction interaction)
	{
		if (interaction.isTake() && guestSitting && !table.hasGottenBill && !currentUser.hasEaten)
		{
			boolean canHold = player.canHoldItem();
			if (canHold)
			{
				currentUser.requestBehavior(GuestBehaviorType.WAITING_FOR_FOOD);
				
				currentUser.hasOrdered = true;
				NpcService.guestGiveOrderTo(currentUser, player);
			}
		}
	}

	@Override
	public Color getColor()
	{
		return new Color(255, 235, 150);
	}
}
