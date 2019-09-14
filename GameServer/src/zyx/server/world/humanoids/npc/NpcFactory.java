package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.npc.behavior.chef.*;
import zyx.server.world.humanoids.npc.behavior.chef.finding.*;
import zyx.server.world.humanoids.npc.behavior.chef.using.*;
import zyx.server.world.humanoids.npc.behavior.cleaner.*;
import zyx.server.world.humanoids.npc.behavior.guest.*;
import zyx.server.world.humanoids.npc.naming.NpcNameGenerator;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

class NpcFactory
{
	static Cleaner createCleaner()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Cleaner cleaner = new Cleaner(setup);
		
		cleaner.addBehavior(new CleanerIdleBehavior(cleaner));
		cleaner.addBehavior(new CleanerGoingToTableBehavior(cleaner));
		cleaner.addBehavior(new CleanerGoingToFloorPosBehavior(cleaner));
		cleaner.addBehavior(new CleanerGoingToDishWasherBehavior(cleaner));
		cleaner.addBehavior(new CleanerWaitingForDishWasherBehavior(cleaner));
		cleaner.addBehavior(new CleanerGoingToIdleBehavior(cleaner));
		
		cleaner.requestBehavior(CleanerBehaviorType.GOING_TO_IDLE);
		
		return cleaner;
	}
	
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
	
	private static final int MS_PER_MIN = 1000 * 60;
	private static final int GUEST_MIN_TIP = 100;
	
	static Guest createGuest()
	{
		NpcSetup setup = NpcNameGenerator.generateSetup();
		Guest guest = new Guest(setup);
		
		guest.maxWaitTime = (int) ((MS_PER_MIN * 5) + (MS_PER_MIN * 2 * Math.random()));
		guest.baseTip = (int) (GUEST_MIN_TIP + (GUEST_MIN_TIP * Math.random()));
		
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
