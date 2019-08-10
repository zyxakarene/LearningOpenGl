package zyx.server.world.humanoids.handheld.guests;

import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemType;
import zyx.server.world.humanoids.handheld.food.DishType;

public class GuestOrder extends HandheldItem
{
	private DishType[] dishes;
	
	public GuestOrder(DishType[] dishes)
	{
		super(HandheldItemType.ORDERS);
		
		this.dishes = dishes;
	}

	public DishType[] getDishes()
	{
		return dishes;
	}

	@Override
	public void process()
	{
	}

}
