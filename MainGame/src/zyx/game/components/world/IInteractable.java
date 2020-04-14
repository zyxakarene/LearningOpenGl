package zyx.game.components.world;

import zyx.utils.interfaces.IPhysbox;

public interface IInteractable
{
	public boolean isInteractable();
	public IPhysbox getInteractionPhysbox();
}
