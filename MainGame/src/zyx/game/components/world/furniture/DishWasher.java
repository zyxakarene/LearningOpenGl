package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.FoodItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;

public class DishWasher extends BaseFurnitureItem<SimpleMesh>
{

	private InteractionAction[] allOptions;

	public DishWasher()
	{
		super(false);

		allOptions = new InteractionAction[]
		{
			InteractionAction.CLOSE,
			InteractionAction.CLEANUP,
		};
	}

	@Override
	protected String getResource()
	{
		return "mesh.box";
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		addChild(item);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		removeChild(item);
	}

	@Override
	public boolean isInteractable()
	{
		return false;
	}

	@Override
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		options.add(InteractionAction.CLOSE);
		
		if (GameModels.player.carriedItem instanceof FoodItem)
		{
			options.add(InteractionAction.CLEANUP);
		}
		
		return options;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return allOptions;
	}
}
