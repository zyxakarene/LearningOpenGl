package zyx.game.components.world.items;

import java.util.ArrayList;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.vo.HandheldItemType;

public class BillItem extends GameItem
{

	private static final ArrayList<InteractionAction> EMPTY_LIST = new ArrayList<>();
	private static final InteractionAction[] EMPTY_ARRAY = new InteractionAction[0];

	public BillItem()
	{
		super(HandheldItemType.BILL);
	}

	@Override
	protected String getItemResource()
	{
		return "mesh.furniture.bill";
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
