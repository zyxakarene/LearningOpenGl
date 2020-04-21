package zyx.game.components.world.items;

import java.util.ArrayList;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.vo.BillState;
import zyx.game.vo.HandheldItemType;

public class BillItem extends GameItem<BillState>
{

	private static final ArrayList<InteractionAction> EMPTY_LIST = new ArrayList<>();

	public BillItem()
	{
		super(HandheldItemType.BILL, BillState.DEFAULT);
	}

	@Override
	protected String getItemResource()
	{
		return "mesh.furniture.bill";
	}

	@Override
	public ArrayList<InteractionAction> getInteractions()
	{
		return EMPTY_LIST;
	}
}
