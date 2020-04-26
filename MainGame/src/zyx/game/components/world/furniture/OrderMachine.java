package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;

public class OrderMachine extends BaseFurnitureItem<SimpleMesh>
{
	
	private static final ArrayList<InteractionAction> OPTIONS = new ArrayList<>();
	static
	{
		OPTIONS.add(InteractionAction.ADD_ORDER);
		OPTIONS.add(InteractionAction.PRINT_BILL);
	}
	
	public OrderMachine()
	{
		super(false);
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.order_machine";
	}

	@Override
	public boolean isInteractable()
	{
		return true;
	}

	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		return OPTIONS;
	}
}
