package zyx.server.world.interactable.chef;

import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class ChefItem extends BaseInteractableItem<Chef> implements IUpdateable
{

	
	public boolean inUse;
	public Chef currentChef;


	private int timeToComplete;
	private int completeTimeRemaining;

	public ChefItem(int completionTime)
	{
		inUse = false;
		currentChef = null;
		
		timeToComplete = completionTime;
	}

	protected void startUsing(Chef user)
	{
		inUse = true;
		currentChef = user;
		completeTimeRemaining = timeToComplete;
	}
	
	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (inUse && currentChef != null)
		{
			completeTimeRemaining -= elapsedTime;
			
			if (completeTimeRemaining <= 0)
			{
				onUsingCompleted();
				
				inUse = false;
				currentChef = null;
			}
		}
	}

	protected abstract void onUsingCompleted();
}
