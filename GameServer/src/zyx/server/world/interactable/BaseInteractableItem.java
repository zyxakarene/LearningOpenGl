package zyx.server.world.interactable;

import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.HumanoidEntity;

public abstract class BaseInteractableItem<User extends HumanoidEntity> extends WorldEntity
{
	public abstract void interactWith(User user);
}
