package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;

public class Table extends BaseFurnitureItem<SimpleMesh>
{

	private static final InteractionAction[] ALL_OPTIONS = new InteractionAction[]
	{
		InteractionAction.CLOSE,
		InteractionAction.PLACE,
		InteractionAction.SERVE_BILL,
	};

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
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		ArrayList<InteractionAction> options = new ArrayList<>();
		options.add(InteractionAction.CLOSE);
		options.add(InteractionAction.SERVE_BILL);
		
		if (GameModels.player.carriedItem != null)
		{
			options.add(InteractionAction.SERVE);
		}
		
		return options;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return ALL_OPTIONS;
	}

}
