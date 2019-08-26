package zyx.game.components.world.items;

import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;

public class FoodItem extends GameItem
{
	private DishType dish;
	
	public FoodItem(DishType dish)
	{
		super(HandheldItemType.INGREDIENTS);
		this.dish = dish;
	}

	@Override
	public void setType(HandheldItemType type)
	{
		super.setType(type);
		load();
	}
	
	@Override
	protected String getItemResource()
	{
		String foodBase = dish.toString().toLowerCase();
		String foodState;
		
		switch (type)
		{
			case INGREDIENTS:
				foodState = "_raw";
				break;
			case POT:
				foodState = "_cooking";
				break;
			case DIRTY_PLATE:
				foodState = "_dirty";
				break;
			default:
				foodState = "";
				break;
		}
		
		System.out.println("====");
		System.out.println("Loading " + "mesh.furniture." + foodBase + foodState);
		System.out.println("====");
		return "mesh.furniture." + foodBase + foodState;
	}
}
