package udp;

import zyx.net.io.connections.ConnectionData;

public class Player
{

	public float x, y, z;
	
	public final ConnectionData connection;
	public final int id;

	public Player(int id, ConnectionData connection)
	{
		this.id = id;
		this.connection = connection;

		x = 0;
		y = 0;
		z = 0;
	}
}
