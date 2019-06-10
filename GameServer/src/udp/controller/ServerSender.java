package udp.controller;

import java.util.ArrayList;
import zyx.net.io.connections.ConnectionData;
import zyx.net.io.controllers.NetworkServerChannel;

public class ServerSender
{
	private static ArrayList<ConnectionData> activeConnections = new ArrayList<>();
	
	public static void sendToAll(String command, Object... params)
	{
		ConnectionData[] connections = activeConnections.toArray(new ConnectionData[activeConnections.size()]);
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}
	
	public static void sendToAllBut(String command, ConnectionData connection, Object... params)
	{
		ArrayList<ConnectionData> clone = (ArrayList<ConnectionData>) activeConnections.clone();
		clone.remove(connection);
		
		ConnectionData[] connections = clone.toArray(new ConnectionData[clone.size()]);
		NetworkServerChannel.sendRequestMulti(command, connections, params);
	}
	
	public static void sendToSingle(String command, ConnectionData connection, Object... params)
	{
		NetworkServerChannel.sendRequestSingle(command, connection, params);
	}
	
	public static void addConnection(ConnectionData connection)
	{
		activeConnections.add(connection);
	}
}
