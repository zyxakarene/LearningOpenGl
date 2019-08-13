package zyx.server.world.interactable.common.player;

import zyx.server.world.humanoids.players.Player;

public interface IPlayerInteractable
{
	public abstract void interactWith(Player player, PlayerInteraction interaction);
}
