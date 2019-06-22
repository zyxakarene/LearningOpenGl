package zyx.server.players;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.PlayerPositionData;
import zyx.net.io.connections.ConnectionData;

public class Player
{

	public float x, y, z;
	public float rx, ry, rz;
	
	public final ConnectionData connection;
	public final int id;
	public final String name;

	public Player(int id, String name, ConnectionData connection)
	{
		this.id = id;
		this.name = name;
		this.connection = connection;

		x = 0;
		y = 0;
		z = 0;
		
		rx = 0;
		ry = 0;
		rz = 0;
	}

	public void updateFrom(PlayerPositionData data)
	{
		Vector3f pos = data.position;
		Vector3f rot = data.rotation;
		
		x = pos.x;
		y = pos.y;
		z = pos.z;
		
		rx = rot.x;
		ry = rot.y;
		rz = rot.z;
	}
}
