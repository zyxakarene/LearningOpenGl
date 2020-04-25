package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.BillItem;
import zyx.game.components.world.items.FoodItem;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;

public class Table extends BaseFurnitureItem<SimpleMesh>
{

	public Table()
	{
		super(false);
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		item.setPosition(true, 0, 43, 0);
		item.setRotation(0, 0, 0);
		addChild(item);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		removeChild(item);
	}

	@Override
	protected String getResource()
	{
		return "mesh.furniture.table";
	}

	@Override
	public boolean isInteractable()
	{
		return true;
	}

	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		options.add(InteractionAction.CLOSE);
		
		GameItem item = GameModels.player.carriedItem;
		if (item != null)
		{
			if (item instanceof BillItem)
			{
				options.add(InteractionAction.SERVE_BILL);
			}
			else
			{
				if (item instanceof FoodItem)
				{
					options.add(InteractionAction.SERVE);
				}

				options.add(InteractionAction.PLACE);
			}
		}
		
		return options;
	}
}
