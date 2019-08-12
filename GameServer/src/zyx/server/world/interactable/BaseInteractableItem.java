package zyx.server.world.interactable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.HumanoidEntity;

public abstract class BaseInteractableItem<User extends HumanoidEntity> extends WorldEntity
{

	public boolean inUse;
	public User currentUser;

	public void claimOwnership(User user)
	{
		if (!inUse)
		{
			inUse = true;
			currentUser = user;
		}
	}
	
	public void makeAvailible()
	{
		inUse = false;
		currentUser = null;
	}

	public boolean canUse(User user)
	{
		return !inUse || (inUse && currentUser == user);
	}

	public abstract void interactWith(User user);
	
	@Override
	protected void onDraw(Graphics g)
	{
		String name = getClass().getSimpleName().toLowerCase();
		name = name.replace("chef", "");
		if (name.length() > 6)
		{
			name = name.substring(0, 6);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		g.drawString(name, (int)x - (getSize() / 2), (int)y);
	}
}
