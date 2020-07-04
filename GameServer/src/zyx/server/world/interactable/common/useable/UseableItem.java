package zyx.server.world.interactable.common.useable;

import java.awt.Color;
import java.awt.Graphics;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.FurnitureService;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class UseableItem<User extends HumanoidEntity> extends BaseInteractableItem<User> implements IUpdateable
{

	private float timeToComplete;
	private float completeTimeRemaining;

	private boolean actionStarted;
	private int currentActionUser;

	public UseableItem(int completionTime, FurnitureType type)
	{
		super(type);
		
		inUse = false;
		currentUser = null;
		actionStarted = false;

		timeToComplete = completionTime;
	}

	protected void startUsing(User user)
	{
		if (currentUser == user)
		{
			currentActionUser = user.id;
			actionStarted = true;
			completeTimeRemaining = timeToComplete;
			
			FurnitureService.interactWith(this, currentActionUser, true);
		}
	}

	protected void startUsing(Player player)
	{
		if (!inUse)
		{
			currentActionUser = player.id;
			currentUser = null;
			actionStarted = true;
			completeTimeRemaining = timeToComplete;
			
			FurnitureService.interactWith(this, currentActionUser, true);
		}
	}

	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (actionStarted)
		{
			completeTimeRemaining -= elapsedTime;

			if (completeTimeRemaining <= 0)
			{
				FurnitureService.interactWith(this, currentActionUser, false);
				onUsingCompleted();

				currentActionUser = 0;
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
			
			g.setColor(Color.GREEN);
			g.fillRect(xPos, yPos + size, (int)(size * percent), 20);
		}
		
		super.onDraw(g);
	}
}
