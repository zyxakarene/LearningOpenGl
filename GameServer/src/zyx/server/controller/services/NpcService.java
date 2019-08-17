package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.GuestGroup;

public class NpcService
{

	public static void addGuestGroup(GuestGroup group)
	{
		Guest[] guests = group.groupMembers;
		HumanoidEntity[] humanoids = new HumanoidEntity[guests.length];
		System.arraycopy(guests, 0, humanoids, 0, guests.length);

		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.CHARACTER_JOINED_GAME, (Object) humanoids);
	}

	public static void removeGuest(Guest guest)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.CHARACTER_LEFT_GAME, guest.id);
	}
}
