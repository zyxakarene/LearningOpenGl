package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.interactable.BaseInteractableItem;

public class FurnitureService
{

	public static void interactWith(BaseInteractableItem<? extends HumanoidEntity> item, int userId, boolean started)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ENTITY_INTERACT, item.id, userId, started);
	}
}
