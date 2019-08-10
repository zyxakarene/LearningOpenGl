package zyx.server.controller;

import zyx.net.io.connections.ConnectionData;
import zyx.net.io.controllers.NetworkServerChannel;
import zyx.server.world.humanoids.players.PlayerManager;

public class ServerSender
{
	public static void sendToAll(String command, Object... params)
	{
		ConnectionData[] connections = PlayerManager.getInstance().getAllConnections();
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}
	
	public static void sendToAllBut(String command, ConnectionData connection, Object... params)
	{
		ConnectionData[] connections =  PlayerManager.getInstance().getAllConnectionsBut(connection);
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}
	
	public static void sendToSingle(String command, ConnectionData connection, Object... params)
	{
		NetworkServerChannel.sendRequestSingle(command, connection, params);
	}
}
