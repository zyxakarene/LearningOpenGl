package zyx.server.world.interactable.common;

import java.awt.Color;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;

public class FoodTable extends CommonTable<Chef>
{

	private static final int MAX_CHEF_TABLE_ITEM_COUNT = 16;

	public FoodTable()
	{
		super(MAX_CHEF_TABLE_ITEM_COUNT);
	}

	@Override
	public void interactWith(Chef chef)
	{
		super.interactWith(chef);

		HandheldItem heldItem = chef.heldItem();

		if (heldItem == null)
		{
			chef.requestBehavior(ChefBehaviorType.IDLE);
		}
	}

	@Override
	public Color getColor()
	{
		return Color.GREEN;
	}
}
