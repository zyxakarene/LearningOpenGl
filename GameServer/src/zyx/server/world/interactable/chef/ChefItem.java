package zyx.server.world.interactable.chef;

import java.awt.Color;
import java.awt.Graphics;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class ChefItem extends BaseInteractableItem<Chef> implements IUpdateable
{

	private float timeToComplete;
	private float completeTimeRemaining;

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

	@Override
	protected void onDraw(Graphics g)
	{
		if (actionStarted)
		{
			int size = getSize();
			int sizeHalf = size / 2;

			int xPos = (int) (x - sizeHalf);
			int yPos = (int) (y - sizeHalf);

			float percent = (timeToComplete - completeTimeRemaining) / timeToComplete;
			System.out.println(percent);
			
			g.setColor(Color.GREEN);
			g.fillRect(xPos, yPos + size, (int)(size * percent), 20);
		}
		
		super.onDraw(g);
	}
}
