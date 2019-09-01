package zyx.server.controller;

import zyx.server.world.humanoids.players.Player;
import zyx.game.world.player.data.PlayerRequestData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import static zyx.server.DebugServerForm.room;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.player.IPlayerInteractable;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.guests.Chair;
import zyx.server.world.interactable.player.OrderMachine;

public class PlayerNetworkCallbacks extends NetworkCallbacks
{

	private final RoomItems roomItems;

	private INetworkCallback onPlayerGetOrder;
	private INetworkCallback onPlayerEnterOrder;
	private INetworkCallback onPlayerPickupItem;
	private INetworkCallback onPlayerGiveItem;
	private INetworkCallback onPlayerGiveBill;

	public PlayerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.PLAYER_GET_ORDER, onPlayerGetOrder);
		registerCallback(NetworkCommands.PLAYER_ENTER_ORDER, onPlayerEnterOrder);
		registerCallback(NetworkCommands.PLAYER_PICKUP_ITEM, onPlayerPickupItem);
		registerCallback(NetworkCommands.PLAYER_GIVE_ITEM, onPlayerGiveItem);
		registerCallback(NetworkCommands.PLAYER_GIVE_BILL, onPlayerGiveBill);

		roomItems = RoomItems.instance;
	}

	private void onPlayerGetOrder(PlayerRequestData data)
	{
		Player player = PlayerManager.getInstance().getEntity(data.playerId);
		BaseNpc npc = NpcManager.getInstance().getEntity(data.npcId);

		if (npc != null && npc instanceof Guest)
		{
			Guest guest = (Guest) npc;
			if (!guest.hasBill && !guest.hasEaten)
			{
				Chair chair = roomItems.getChairWithGuest(guest);
				if (chair != null)
				{
					chair.interactWith(player, PlayerInteraction.take());
				}
			}
		}
	}

	private void onPlayerEnterOrder(int playerId)
	{
		Player player = PlayerManager.getInstance().getEntity(playerId);
		if (player != null)
		{
			OrderMachine machine = room.getOrderMachine();
			machine.interactWith(player);
		}
	}

	private void onPlayerPickupItem(PlayerRequestData data)
	{
		Player player = PlayerManager.getInstance().getEntity(data.playerId);
		IPlayerInteractable entity = room.getEntityWithItem(data.itemId);

		if (player != null && entity != null)
		{
			entity.interactWith(player, PlayerInteraction.take(data.itemId));
		}
	}

	private void onPlayerGiveItem(PlayerRequestData data)
	{
		Player player = PlayerManager.getInstance().getEntity(data.playerId);
		IPlayerInteractable entity = room.getEntityWithId(data.ownerId);

		if (player != null && entity != null)
		{
			entity.interactWith(player, PlayerInteraction.give());
		}
	}

	private void onPlayerGiveBill(PlayerRequestData data)
	{
		Player player = PlayerManager.getInstance().getEntity(data.playerId);
		IPlayerInteractable entity = room.getEntityWithId(data.ownerId);

		if (entity instanceof DinnerTable)
		{
			DinnerTable table = (DinnerTable) entity;
			
			if (table.canReceiveBill())
			{
				player.pickupItemSilent(new BillItem());
				table.interactWith(player, PlayerInteraction.give());
			}
		}
	}

	private void createCallbacks()
	{
		onPlayerGetOrder = (INetworkCallback<PlayerRequestData>) (PlayerRequestData data)
				-> 
				{
					onPlayerGetOrder(data);
		};

		onPlayerEnterOrder = (INetworkCallback<Integer>) (Integer data)
				-> 
				{
					onPlayerEnterOrder(data);
		};

		onPlayerPickupItem = (INetworkCallback<PlayerRequestData>) (PlayerRequestData data)
				-> 
				{
					onPlayerPickupItem(data);
		};

		onPlayerGiveItem = (INetworkCallback<PlayerRequestData>) (PlayerRequestData data)
				-> 
				{
					onPlayerGiveItem(data);
		};

		onPlayerGiveBill = (INetworkCallback<PlayerRequestData>) (PlayerRequestData data)
				-> 
				{
					onPlayerGiveBill(data);
		};
	}
}