package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.behavior.chef.ChefIdleBehavior;
import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.npc.behavior.guest.GuestIdleBehavior;
import zyx.server.world.humanoids.npc.naming.NpcNameGenerator;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

class NpcFactory
{
	static Chef createChef()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Chef chef = new Chef(setup);
		
		chef.addBehavior(new ChefIdleBehavior(chef));
		
		chef.requestBehavior(ChefBehaviorType.IDLE);
		
		return chef;
	}
	
	static Guest createGuest()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Guest guest = new Guest(setup);
		
		guest.addBehavior(new GuestIdleBehavior(guest));
		guest.requestBehavior(GuestBehaviorType.IDLE);
		
		return guest;
	}
}
