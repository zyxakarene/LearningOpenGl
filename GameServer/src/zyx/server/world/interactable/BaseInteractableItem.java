package zyx.server.world.interactable;

import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.HumanoidEntity;

public abstract class BaseInteractableItem<User extends HumanoidEntity> extends WorldEntity
{

	public boolean inUse;
	public User currentUser;

	public void claimOwnership(User user)
	{
		if (!inUse)
		{
			inUse = true;
			currentUser = user;
		}
	}
	
	public void makeAvailible()
	{
		inUse = false;
		currentUser = null;
	}

	public boolean canUse(User user)
	{
		return !inUse || (inUse && currentUser == user);
	}

	public abstract void interactWith(User user);
}
