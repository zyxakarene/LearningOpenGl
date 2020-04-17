package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.InteractionAction;

public class OrderMachine extends BaseFurnitureItem<SimpleMesh>
{

	private static final InteractionAction[] ALL_OPTIONS = new InteractionAction[]
	{
		InteractionAction.CLOSE,
		InteractionAction.ADD_ORDER
	};
	
	private static final ArrayList<InteractionAction> OPTIONS = new ArrayList<>();
	static
	{
		OPTIONS.add(InteractionAction.CLOSE);
		OPTIONS.add(InteractionAction.ADD_ORDER);
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
		return false;
	}

	@Override
	public ArrayList<InteractionAction> getAvailibleInteractions()
	{
		return OPTIONS;
	}

	@Override
	public InteractionAction[] getAllInteractions()
	{
		return ALL_OPTIONS;
	}
}
