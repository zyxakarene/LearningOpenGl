package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.npc.behavior.chef.*;
import zyx.server.world.humanoids.npc.behavior.chef.finding.*;
import zyx.server.world.humanoids.npc.behavior.chef.using.*;
import zyx.server.world.humanoids.npc.behavior.guest.*;
import zyx.server.world.humanoids.npc.naming.NpcNameGenerator;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

class NpcFactory
{
	static Chef createChef()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Chef chef = new Chef(setup);
		
		chef.addBehavior(new ChefIdleBehavior(chef));
		chef.addBehavior(new ChefFindingMonitorBehavior(chef));
		chef.addBehavior(new ChefGettingRecipeBehavior(chef));
		chef.addBehavior(new ChefFindingFridgeBehavior(chef));
		chef.addBehavior(new ChefGettingIngredientsBehavior(chef));
		chef.addBehavior(new ChefFindingStoveBehavior(chef));
		chef.addBehavior(new ChefCookingFoodBehavior(chef));
		chef.addBehavior(new ChefFindingTableBehavior(chef));
		chef.addBehavior(new ChefServingFoodBehavior(chef));
		
		chef.requestBehavior(ChefBehaviorType.IDLE);
		
		return chef;
	}
	
	static Guest createGuest()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Guest guest = new Guest(setup);
		
		guest.addBehavior(new GuestIdleBehavior(guest));
		guest.addBehavior(new GuestWalkToChairBehavior(guest));
		guest.addBehavior(new GuestWaitingForOrderBehavior(guest));
		guest.addBehavior(new GuestWaitingForFoodBehavior(guest));
		guest.addBehavior(new GuestEatingBehavior(guest));
		guest.addBehavior(new GuestWaitingForBillBehavior(guest));
		guest.addBehavior(new GuestWalkingOutBehavior(guest));
		guest.requestBehavior(GuestBehaviorType.IDLE);
		
		return guest;
	}
}
