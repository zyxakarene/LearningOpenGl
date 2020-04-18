package zyx.server.world.humanoids.players;

import java.awt.Color;
import zyx.game.vo.CharacterType;
import zyx.game.vo.Gender;
import zyx.net.io.connections.ConnectionData;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;

public class Player extends HumanoidEntity implements IUpdateable
{

	public final ConnectionData connection;
	
	public Player(String name, Gender gender, ConnectionData connection)
	{
		super(name, gender, CharacterType.PLAYER);
		
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
