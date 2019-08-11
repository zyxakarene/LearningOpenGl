package zyx.server.world.interactable.chef;

import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class ChefItem extends BaseInteractableItem<Chef> implements IUpdateable
{

	private int timeToComplete;
	private int completeTimeRemaining;

	private boolean actionStarted;
	
	public ChefItem(int completionTime)
	{
		inUse = false;
		currentUser = null;
		actionStarted = false;

		timeToComplete = completionTime;
	}

	protected void startUsing(Chef user)
	{
		if (currentUser == user)
		{
			actionStarted = true;
			completeTimeRemaining = timeToComplete;
		}
	}

	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (actionStarted && currentUser != null)
		{
			completeTimeRemaining -= elapsedTime;

			if (completeTimeRemaining <= 0)
			{
				onUsingCompleted();

				actionStarted = false;
				makeAvailible();
			}
		}
	}
	
	protected abstract void onUsingCompleted();
}
