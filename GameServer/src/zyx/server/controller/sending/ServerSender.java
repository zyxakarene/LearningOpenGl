package zyx.server.controller.sending;

import zyx.net.io.connections.ConnectionData;
import zyx.net.io.controllers.NetworkServerChannel;
import zyx.server.world.humanoids.players.PlayerManager;

public class ServerSender
{

	private static void sendToAll(String command, Object[] params)
	{
		ConnectionData[] connections = PlayerManager.getInstance().getAllConnections();
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}

	private static void sendToAllBut(String command, ConnectionData connection, Object[] params)
	{
		ConnectionData[] connections = PlayerManager.getInstance().getAllConnectionsBut(connection);
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}

	private static void sendToSingle(String command, ConnectionData connection, Object[] params)
	{
		NetworkServerChannel.sendRequestSingle(command, connection, params);
	}

	public static void sendWithType(SendType sendType, String command, Object... params)
	{
		switch (sendType.type)
		{
			case ALL:
			{
				sendToAll(command, params);
				break;
			}
			case ALL_BUT:
			{
				sendToAllBut(command, sendType.connection, params);
				break;
			}
			case SINGLE:
			{
				sendToSingle(command, sendType.connection, params);
				break;
			}
		}
	}
}
