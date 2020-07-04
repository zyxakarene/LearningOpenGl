package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.interactable.common.useable.UseableItem;

public class FurnitureService
{

	public static void interactWith(UseableItem item, int userId, boolean started)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ENTITY_INTERACT, item.id, userId, started);
	}
}
