package zyx.server.world.humanoids.handheld.guests;

import java.util.ArrayList;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemType;
import zyx.server.world.humanoids.handheld.food.DishType;
import zyx.server.world.interactable.common.DinnerTable;

public class GuestOrder extends HandheldItem
{
	private ArrayList<DishType> dishes;
	private DinnerTable table;
	
	public GuestOrder(DinnerTable table)
	{
		super(HandheldItemType.ORDERS);
		
		this.dishes = new ArrayList<>();
		this.table = table;
	}

	public boolean isMatchingTable(DinnerTable table)
	{
		return this.table == table;
	}

	public void addDish(DishType dish)
	{
		dishes.add(dish);
	}
	
	public ArrayList<DishType> getDishes()
	{
		return dishes;
	}

	@Override
	public void process()
	{
	}
	
	@Override
	public String getVisualName()
	{
		String typeName = super.getVisualName();
		return typeName + String.format(" - %s", dishes);
	}

}
