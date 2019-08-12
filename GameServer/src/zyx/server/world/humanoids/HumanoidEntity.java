package zyx.server.world.humanoids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import zyx.game.vo.Gender;
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
			System.out.println(item + " was removed from " + this);
		}

		heldItem = null;
		return item;
	}

	public void pickupItem(HandheldItem item)
	{
		System.out.println(this + " picked up " + item);
		heldItem = item;
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
			g.fillRect(xPos - 30, yPos-8, 60, 8);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 8)); 
			g.drawString(heldItem.getVisualName(), xPos - 30, yPos - 1);
			g.drawRect(xPos - 31, yPos-9, 61, 9);
		}
	}

	public boolean wantsToHold(HandheldItem item)
	{
		return true;
	}
}
