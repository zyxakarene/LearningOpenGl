package zyx.server.world.interactable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.FurnitureType;
import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.HumanoidEntity;

public abstract class BaseInteractableItem<User extends HumanoidEntity> extends WorldEntity
{

	public boolean inUse;
	public User currentUser;
	
	public final FurnitureType type;

	public BaseInteractableItem(FurnitureType type)
	{
		this.type = type;
	}
	
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
	
	public boolean isPersistentUsing()
	{
		return false;
	}

	public boolean canUse(User user)
	{
		return !inUse || (inUse && currentUser == user);
	}

	public void getUsingPosition(Vector3f pos, Vector3f lookPos)
	{
		float halfSize = getSize() / 2f;
		getDir(HELPER_DIR);
		
		pos.x = x + (HELPER_DIR.x * halfSize);
		pos.y = y + (HELPER_DIR.y * halfSize);
		pos.z = z + (HELPER_DIR.z * halfSize);
		
		lookPos.x = x;
		lookPos.y = y;
		lookPos.z = z;
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
