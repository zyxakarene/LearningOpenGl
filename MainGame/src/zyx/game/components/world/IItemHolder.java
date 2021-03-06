package zyx.game.components.world;

import zyx.game.components.world.interactable.IInteractable;
import zyx.game.components.world.items.GameItem;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public interface IItemHolder extends IDisposeable, IUpdateable, IInteractable
{
	public int getUniqueId();
	public void hold(GameItem item);
	public void removeItem(GameItem item);
}
