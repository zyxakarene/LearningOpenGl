package zyx.server.world.humanoids.players;

import java.awt.Color;
import zyx.game.vo.Gender;
import zyx.net.io.connections.ConnectionData;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.guests.GuestOrder;

public class Player extends HumanoidEntity implements IUpdateable
{

	public final ConnectionData connection;
	
	public Player(String name, Gender gender, ConnectionData connection)
	{
		super(name, gender);
		
		this.connection = connection;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (heldItem != null)
		{
			heldItem.update(timestamp, elapsedTime);
		}
	}
	
	public GuestOrder getHeldAsOrders()
	{
		if (heldItem instanceof GuestOrder)
		{
			return (GuestOrder) heldItem;
		}
		
		return null;
	}

	@Override
	public boolean isRound()
	{
		return true;
	}

	@Override
	public Color getColor()
	{
		return Color.ORANGE;
	}
}
