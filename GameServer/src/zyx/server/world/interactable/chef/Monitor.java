package zyx.server.world.interactable.chef;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.DishType;
import zyx.game.vo.FurnitureType;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.common.useable.UseableItem;

public class Monitor extends UseableItem<Chef>
{

	private static final int LOOK_TIME = 1000;

	private LinkedList<FoodItem> queue;

	public Monitor()
	{
		super(LOOK_TIME, FurnitureType.MONITOR);

		queue = new LinkedList<>();
	}

	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			HandheldItem heldItem = chef.heldItem();
			if (heldItem == null && !queue.isEmpty())
			{
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		FoodItem recipe = queue.removeFirst();
		currentUser.pickupItemSilent(recipe);
		currentUser.requestBehavior(ChefBehaviorType.FINDING_FRIDGE);
	}

	public void addDish(DishType dish)
	{
		FoodItem recipe = new FoodItem(dish);
		queue.addLast(recipe);
	}

	public boolean hasOrders()
	{
		return !queue.isEmpty();
	}

	@Override
	public Color getColor()
	{
		return Color.LIGHT_GRAY;
	}

	@Override
	protected void onDraw(Graphics g)
	{
		super.onDraw(g);

		int size = getSize();
		int sizeHalf = size / 2;

		int xPos = (int) (x - sizeHalf);
		int yPos = (int) (y - sizeHalf);

		g.drawString("Orders: " + queue.size(), xPos, yPos + size - 1);
	}
}
