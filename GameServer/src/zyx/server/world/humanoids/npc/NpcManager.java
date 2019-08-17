package zyx.server.world.humanoids.npc;

import zyx.server.utils.IUpdateable;
import zyx.server.world.entity.WorldEntityManager;

public class NpcManager extends WorldEntityManager<BaseNpc> implements IUpdateable
{
	private static final NpcManager INSTANCE = new NpcManager();

	public static NpcManager getInstance()
	{
		return INSTANCE;
	}
	
	private NpcManager()
	{
	}

	public Cleaner addCleaner()
	{
		Cleaner cleaner = NpcFactory.createCleaner();
		addEntity(cleaner);
		
		return cleaner;
	}

	public Chef addChef()
	{
		Chef chef = NpcFactory.createChef();
		addEntity(chef);
		
		return chef;
	}

	public GuestGroup addGuestGroup()
	{
		int size = 1;
		GuestGroup group = new GuestGroup(size);
		
		for (Guest guest : group.groupMembers)
		{
			addEntity(guest);
		}
		
		return group;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		int count = entities.size() - 1;
		for (int i = count; i >= 0; i--)
		{
			BaseNpc npc = entities.get(i);
			npc.update(timestamp, elapsedTime);
		}
	}
}
