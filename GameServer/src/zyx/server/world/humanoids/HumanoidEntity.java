package zyx.server.world.humanoids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import zyx.game.vo.Gender;
import zyx.server.controller.services.ItemService;
import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;

public class HumanoidEntity extends WorldEntity
{

	public final String name;
	public final Gender gender;

	protected HandheldItem heldItem;

	public HumanoidEntity(String name, Gender gender)
	{
		this.name = name;
		this.gender = gender;
	}

	public HandheldItem heldItem()
	{
		return heldItem;
	}

	public boolean isHoldingAnything()
	{
		return heldItem != null;
	}

	public boolean canHoldItem()
	{
		return heldItem == null;
	}

	public HandheldItem removeItem()
	{
		HandheldItem item = heldItem;

		if (item != null)
		{
			heldItem.ownerId = -1;
			heldItem.inUse = false;
			ItemService.setInUse(heldItem, false);
			
			System.out.println(item + " was removed from " + this);
			item.dispose();
		}

		heldItem = null;
		return item;
	}

	public HandheldItem removeItemSilent()
	{
		HandheldItem item = heldItem;

		if (item != null)
		{
			item.inUse = false;
			System.out.println(item + " was removed from " + this);
		}

		heldItem = null;
		return item;
	}

	public void pickupItem(HandheldItem item)
	{
		ItemService.setOwner(item, id);
		ItemService.setInUse(item, true);
		
		System.out.println(this + " picked up " + item);
		heldItem = item;
		heldItem.inUse = true;
		heldItem.ownerId = id;
	}

	public void pickupItemSilent(HandheldItem item)
	{
		System.out.println(this + " picked up " + item);
		heldItem = item;
		heldItem.ownerId = id;
		heldItem.inUse = true;
	}

	@Override
	protected void onDraw(Graphics g)
	{
		super.onDraw(g);

		if (heldItem != null)
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
			g.drawString(heldItem.getVisualName(), xPos - 30, yPos - 1);
			g.drawRect(xPos - 31, yPos - 9, 61, 9);
		}

		double angle = Math.atan2(ly - y, lx - x);
		int toX = (int) (Math.round(x + (20 * Math.cos(angle))));
		int toY = (int) (Math.round(y + (20 * Math.sin(angle))));
		g.setColor(Color.BLACK);
		g.drawLine((int) x, (int) y, (int) toX, (int) toY);
	}

	public boolean wantsToHold(HandheldItem item)
	{
		return true;
	}
}
