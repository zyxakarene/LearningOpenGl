package zyx.server.world.interactable.chef;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import zyx.server.controller.services.ItemService;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.common.useable.UseableItem;

public class Stove extends UseableItem<Chef>
{

	private static final int COOK_TIME = 1000;

	private FoodItem foodItem;

	public Stove()
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
				chef.removeItemSilent();
				ItemService.setOwner(foodItem, id);
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

			g.setColor(Color.WHITE);
			g.fillRect(xPos - 30, yPos - 8, 60, 8);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 8));
			g.drawString(foodItem.getVisualName(), xPos - 30, yPos - 1);
			g.drawRect(xPos - 31, yPos - 9, 61, 9);
		}
	}

	@Override
	public Color getColor()
	{
		return Color.DARK_GRAY;
	}

}
