package zyx.game.components.world.interactable;

import java.util.ArrayList;
import zyx.utils.interfaces.IPhysbox;

public interface IInteractable extends IPhysbox
{
	public boolean isInteractable();
	public ArrayList<InteractionAction> getAvailibleInteractions();
	public InteractionAction[] getAllInteractions();
}
