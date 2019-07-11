package zyx.server.players;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.PlayerPositionData;
import zyx.net.io.connections.ConnectionData;

public class Player
{

	public float x, y, z;
	public float lx, ly, lz;
	
	public final ConnectionData connection;
	public final int id;
	public final String name;
	
	public boolean updatedPosition;

	public Player(int id, String name, ConnectionData connection)
	{
		this.id = id;
		this.name = name;
		this.connection = connection;

		x = 0;
		y = 0;
		z = 0;
		
		lx = 0;
		ly = 0;
		lz = 100;
	}

	public void updateFrom(PlayerPositionData data)
	{
		updatedPosition = true;
		Vector3f pos = data.position;
		Vector3f rot = data.lookAt;
		
		x = pos.x;
		y = pos.y;
		z = pos.z;
		
		lx = rot.x;
		ly = rot.y;
		lz = rot.z;
	}
}
