package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;

public abstract class NpcFurniture<V extends SimpleMesh> extends BaseFurnitureItem<V>
{

	public NpcFurniture(boolean animated)
	{
		super(animated);
	}

	@Override
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		return EMPTY_LIST;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return EMPTY_ARRAY;
	}
}
