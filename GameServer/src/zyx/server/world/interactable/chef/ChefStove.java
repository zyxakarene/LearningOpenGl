package zyx.server.world.interactable.chef;

import java.awt.Color;
import java.awt.Graphics;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;

public class ChefStove extends ChefItem
{

	private static final int COOK_TIME = 1000;

	private FoodItem foodItem;

	public ChefStove()
	{
		super(COOK_TIME);
	}

	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			foodItem = chef.getHeldAsFood();

			if (foodItem != null)
			{
				foodItem.process();
				chef.removeItem();
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		foodItem.process();
		currentUser.pickupItem(foodItem);

		currentUser.requestBehavior(ChefBehaviorType.FINDING_TABLE);
		foodItem = null;
	}

	@Override
	protected void onDraw(Graphics g)
	{
		super.onDraw(g);

		if (foodItem != null)
		{
			int size = getSize();
			int sizeHalf = size / 2;

			int xPos = (int) (x - sizeHalf);
			int yPos = (int) (y - sizeHalf);
		
			g.setColor(Color.MAGENTA);
			g.fillRect(xPos, yPos, 10, 10);
		}
	}

	@Override
	public Color getColor()
	{
		return Color.DARK_GRAY;
	}

}
