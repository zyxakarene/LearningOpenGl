package zyx.server.world.humanoids.players;

import zyx.game.vo.Gender;
import zyx.net.io.connections.ConnectionData;
import zyx.server.world.humanoids.HumanoidEntity;

public class Player extends HumanoidEntity
{

	public final ConnectionData connection;
	
	public Player(String name, Gender gender, ConnectionData connection)
	{
		super(name, gender);
		
		this.connection = connection;
	}
}
