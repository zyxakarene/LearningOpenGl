package auth;

import java.net.InetAddress;
import udp.Player;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.connections.ConnectionData;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class PlayerJoinedResponse extends BaseNetworkResponse<Player>
{

	public PlayerJoinedResponse()
	{
		super(NetworkCommands.PLAYER_JOINED_GAME);
	}

	@Override
	protected Player onMessageRecieved(ReadableDataObject data)
	{
		int playerId = data.getInteger("id");

		InetAddress senderHost = getSenderHost();
		int senderPort = getSenderPort();
		
		ConnectionData connection = new ConnectionData(senderHost, senderPort);
		Player player = new Player(playerId, connection);
		
		return player;
	}
}
